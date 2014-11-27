import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class Player {
    private static final boolean DEBUG = false;
    private static World world = new World();
    private static ZoneFactory zoneFactory;
    private static InfluenceMap influenceMap;
    private static int MY_ID = 0;
    private static Random rand = new Random();

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int playerCount = in.nextInt(); // the amount of players (2 to 4)
        MY_ID = in.nextInt(); // my player ID (0, 1, 2 or 3)
        int zoneCount = in.nextInt(); // the amount of zones on the map
        int linkCount = in.nextInt(); // the amount of links between all zones
        in.nextLine();
        zoneFactory = new ZoneFactory(in, zoneCount);
        for (int i = 0; i < linkCount; i++) {
            int zone1 = in.nextInt();
            int zone2 = in.nextInt();
            world.add(zone1, zone2, zoneFactory);
            in.nextLine();
        }

        int nbRound = 1;
        while (true) {
            int money = in.nextInt(); // my available Platinum
            in.nextLine();
            Map<Integer, Integer> myZoneList = new HashMap<>();
            Map<Integer, Integer> enemyZoneWithZergsList = new HashMap<>();
            List<Integer> enemyZoneList = new ArrayList<>();
            for (int i = 0; i < zoneCount; i++) {
                int zId = in.nextInt(); // this zone's ID
                int ownerId = in.nextInt(); // the player who owns this zone (-1 otherwise)
                int[] pods = new int[4];
                pods[0] = in.nextInt(); // player 0's PODs on this zone
                pods[1] = in.nextInt(); // player 1's PODs on this zone
                pods[2] = in.nextInt(); // player 2's PODs on this zone (always 0 for a two player game)
                pods[3] = in.nextInt(); // player 3's PODs on this zone (always 0 for a two or three player game)
                zoneFactory.initZones(zId, ownerId, pods);
                if (MY_ID == ownerId && pods[MY_ID] > 0) {
                    myZoneList.put(zId, pods[MY_ID]);
                } else if (ownerId != -1 && ownerId != MY_ID) {
                    enemyZoneList.add(zId);
                    if (pods[ownerId] > 0) {
                        enemyZoneWithZergsList.put(zId, pods[ownerId]);
                    }
                }
                in.nextLine();
            }

            influenceMap = InfluenceMap.from(zoneFactory);

            if (playerCount > 2 && nbRound == 1) {
                execute(Command.WAIT.toString());
                execute(SpawningPool.NO_SPAWN.toString());
            } else if (nbRound == 1 && playerCount == 2 || playerCount > 2 && nbRound == 2) {
                execute(Command.WAIT.toString());
                execute(spawnZergsForFirstTime(money));
            } else {
                Command command = orderZergs(myZoneList);
                execute(command.toString());
                execute(spawnZergs(money, enemyZoneWithZergsList, command, enemyZoneList));
            }

            nbRound++;
        }
    }

    // -----------------------------------------------------------
    // FUNCTIONS
    // -----------------------------------------------------------

    private static <T> void debug(T obj) {
        if (DEBUG) {
            System.err.println(obj.toString());
        }
    }

    private static void execute(String cmd) {
        System.out.println(cmd);
    }

    private static String spawnZergsForFirstTime(int money) {
        SpawningPool spawningPool = new SpawningPool(money);

        Set<Integer> mostInfluencedList = influenceMap.mostInfluencedZones();
        int i = 1;
        for (int zoneId : mostInfluencedList) {
            if (i > 5) {
                break;
            }
            Zone zone = zoneFactory.get(zoneId);
            if (zone.isNeutral()) {
                spawningPool.add(Zergs.spawn(2).in(zoneId));
                i++;
            }
        }

        return spawningPool.toString();
    }

    private static Command orderZergs(Map<Integer, Integer> myZoneList) {
        Command command = new Command();
        for (Map.Entry<Integer, Integer> entryZone : myZoneList.entrySet()) {
//            ZergAction zergAction = computeZergAction(entryZone.getKey());
            ZergAction zergAction = ZergAction.EXPAND;
            switch (zergAction) {
                case ATK:
                    command.addAll(attack(entryZone.getKey(), entryZone.getValue()));
                    break;
                case DEF:
                    command.addAll(defend(entryZone.getKey(), entryZone.getValue()));
                    break;
                case EXPAND:
                default:
                    command.addAll(expand(entryZone.getKey(), entryZone.getValue()));
                    break;
            }
        }
        return command;
    }

    private static List<Order> attack(int zoneId, int nbZergs) {
        List<Order> orderList = new ArrayList<>();
        Zone zone = zoneFactory.get(zoneId);
        for (Zone neighbourZone : zone.linkedZones) {
            if (neighbourZone.isEnemyZone() && neighbourZone.isWealthy() && neighbourZone.allZergs() < nbZergs) {
                int nbZergsToMove = neighbourZone.allZergs() + 1;
                int nbRemainingZergs = nbZergs - nbZergsToMove;
                orderList.add(Order.order(nbZergsToMove).goFrom(zoneId).to(neighbourZone.zoneId));
                if (nbRemainingZergs > 0) {
                    orderList.addAll(expand(zoneId, nbRemainingZergs));
                }
                break;
            }
        }
        return orderList;
    }

    private static List<Order> defend(int zoneId, int nbZergs) {
        List<Order> orderList = new ArrayList<>();
        Zone zone = zoneFactory.get(zoneId);
        int zoneIdToDefend = zoneId;
        int mostWealthyZoneMoney = zone.platinumSource;
        for (Zone neighbourZone : zone.linkedZones) {
            if (neighbourZone.isMyZone() && neighbourZone.platinumSource > mostWealthyZoneMoney) {
                zoneIdToDefend = neighbourZone.zoneId;
            }
        }
        if (zoneId != zoneIdToDefend) {
            orderList.add(Order.order(nbZergs).goFrom(zoneId).to(zoneIdToDefend));
        }
        return orderList;
    }

    private static ZergAction computeZergAction(int zoneId) {
        Zone zone = zoneFactory.get(zoneId);
//        if (zone.isMyZone() && zone.isWealthy() && zone.hasNearbyEnemy()) {
//            return ZergAction.DEF;
//        }
        for (Zone neighbourZone : zone.linkedZones) {
//            if (neighbourZone.isMyZone() && neighbourZone.isWealthy() && neighbourZone.hasNearbyEnemy()) {
//                return ZergAction.DEF;
//            }
            if (neighbourZone.isEnemyZone() && neighbourZone.isWealthy() && neighbourZone.allZergs() < zone.allZergs()) {
                return ZergAction.ATK;
            }
        }
        // TODO: Implement atk
        return ZergAction.EXPAND;
    }

    private static List<Order> expand(int zoneId, int nbZergs) {
        List<Order> orderList = new ArrayList<>();
        int nbZergsLeft = nbZergs;
        Zone zone = zoneFactory.get(zoneId);
        NeighbourInfluenceMap neighbourInfluenceMap = NeighbourInfluenceMap.from(zone);
        if (neighbourInfluenceMap.hasAtLeastOneFavorableNeighbour()) {
            // Go to most favorable neighbours
            for (int neighbourZoneId : neighbourInfluenceMap.mostInfluencedZones()) {
                if (nbZergsLeft == 0) {
                    break;
                }
                Zone neighbourZone = zoneFactory.get(neighbourZoneId);
                if (neighbourZone.hasEnemy()) {
                    int nbZergToMove = neighbourZone.allZergs();
                    if (nbZergsLeft > nbZergToMove) {
                        orderList.add(Order.order(nbZergToMove).goFrom(zoneId).to(neighbourZoneId));
                        nbZergsLeft -= nbZergToMove;
                    }
                } else{
                    orderList.add(Order.order(1).goFrom(zoneId).to(neighbourZoneId));
                    nbZergsLeft--;
                }
            }
        } else {
            // Find the closest most favorable neighbours instead of idling
//            List<Integer> targetZoneId = findClosestFavorableNeighbour(zone, new LinkedList<Integer>(), 0);
//            if (targetZoneId.size() > 0) {
//                debug("zoneId = " + zoneId + " - targetZoneId = " + targetZoneId);
//                orderList.add(Order.order(nbZergsLeft).goFrom(zoneId).to(targetZoneId.get(0)));
//            }
            do {
                int index = rand.nextInt(zone.linkedZones.size());
                int targetZoneId = zone.linkedZones.get(index).zoneId;
                orderList.add(Order.order(1).goFrom(zoneId).to(targetZoneId));
                nbZergsLeft--;
            } while (nbZergsLeft > 0);
        }
        return orderList;
    }

    private static List<Integer> findClosestFavorableNeighbour(Zone zone, List<Integer> zoneIdList, int iteration) {
        if (iteration > 10) {
            return new LinkedList<>();
        }
        for (Zone neighbourZone : zone.linkedZones) {
            if (influenceMap.getInfluenceOf(neighbourZone.zoneId) > 0) {
                zoneIdList.add(neighbourZone.zoneId);
                return zoneIdList;
            }
        }
        for (Zone neighbourZone : zone.linkedZones) {
            if (zoneIdList.contains(neighbourZone.zoneId)) {
                continue;
            }
            List<Integer> newZoneIdList = new LinkedList<>(zoneIdList);
            newZoneIdList.add(neighbourZone.zoneId);
            newZoneIdList = findClosestFavorableNeighbour(neighbourZone, newZoneIdList, iteration + 1);
            if (newZoneIdList.size() > 0) {
                return newZoneIdList;
            }
        }
        return new LinkedList<>();
    }


    private static String spawnZergs(int money, Map<Integer, Integer> enemyZoneWithZergsList, Command command, List<Integer> enemyZoneList) {
        SpawningPool spawningPool = new SpawningPool(money);
        // Fetch the remaining zone that are not conquered yet
        List<Zone> neutralWealthyZones = new ArrayList<>();
        for (Zone zone : zoneFactory.zoneMap.values()) {
            if (zone.isNeutral() && zone.isWealthy()) {
                neutralWealthyZones.add(zone);
            }
        }
        if (!neutralWealthyZones.isEmpty()) {
            Collections.sort(neutralWealthyZones, Zone.MOST_WEALTHY);
            // FIXME: Compute what's the best value to spawn zergs
            int nbZergsToSpawn = spawningPool.computeNbSpawns() / 2;
            nbZergsToSpawn = nbZergsToSpawn > neutralWealthyZones.size() ? neutralWealthyZones.size() : nbZergsToSpawn;
            for (int i = 0; i < nbZergsToSpawn; i++) {
                spawningPool.add(Zergs.spawn(1).in(neutralWealthyZones.get(i).zoneId));
            }
        }
        // Defend
        for (int enemyZoneId : enemyZoneWithZergsList.keySet()) {
            Zone enemyZone = zoneFactory.get(enemyZoneId);
            for (Zone neighbourZone : enemyZone.linkedZones) {
                if (neighbourZone.isMyZone() && neighbourZone.isWealthy()) {
                    int nbZergsToSpawn = enemyZone.allZergs();
                    for (Order order : command) {
                        if (order.to == neighbourZone.zoneId) {
                            nbZergsToSpawn -= order.nbZergs;
                        }
                    }
                    spawningPool.add(Zergs.spawn(nbZergsToSpawn).in(neighbourZone.zoneId));
                }
            }
        }
        // Atk
        int nbSpawnsForAtk = spawningPool.computeNbSpawns() / 5;
        if (nbSpawnsForAtk > 1) {
            Map<Integer, Integer> map = new HashMap<>();
            for (Zone zone : zoneFactory.zoneMap.values()) {
                if (zone.isNeutral() ) {
                    map.put(zone.zoneId, influenceMap.getInfluenceOf(zone.zoneId));
                }
            }
            InfluenceComparator comparator = new InfluenceComparator(map);
            Map<Integer, Integer> sortedMap = new TreeMap<>(comparator);
            sortedMap.putAll(map);
            for (int zoneId : sortedMap.keySet()) {
                if (nbSpawnsForAtk == 0) {
                    break;
                }
                spawningPool.add(Zergs.spawn(1).in(zoneId));
                nbSpawnsForAtk--;
            }
            if (nbSpawnsForAtk > 0) {
                // Spawn near an enemy
                finishAtk: for (int enemyZoneId : enemyZoneList) {
                    Zone enemyZone = zoneFactory.get(enemyZoneId);
                    for (Zone zone : enemyZone.linkedZones) {
                        if (zone.isMyZone()) {
                            debug("ATK - " + nbSpawnsForAtk + " in " + zone.zoneId + " - enemyZone " + enemyZone.zoneId);
                            spawningPool.add(Zergs.spawn(nbSpawnsForAtk).in(zone.zoneId));
                            break finishAtk;
                        }
                    }
                }

            }
        }

        return spawningPool.toString();
    }

    // -----------------------------------------------------------
    // ZONE / CONTINENT / WORLD
    // -----------------------------------------------------------

    private static class World extends ArrayList<Continent> {
        public boolean add(int zoneId1, int zoneId2, ZoneFactory zoneFactory) {
            zoneFactory.link(zoneId1, zoneId2);

            for (Continent continent : this) {
                if (continent.hasZone(zoneId1) || continent.hasZone(zoneId2)) {
                    continent.add(zoneId1, zoneFactory.get(zoneId2));
                    continent.add(zoneId2, zoneFactory.get(zoneId1));
                    return true;
                }
            }
            // No one has zone1 and zone2 => Creating a new continent
            Continent continent = new Continent();
            continent.add(zoneId1, zoneFactory.get(zoneId2));
            continent.add(zoneId2, zoneFactory.get(zoneId1));
            this.add(continent);
            return true;
        }

        public World copy() {
            World world = new World();
            Collections.copy(world, this);
            return world;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (Continent continent : this) {
                sb.append("------ Continent ").append(i++).append(" ------\n");
                sb.append(continent.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    private static class Continent {
        Map<Integer, List<Zone>> zoneMap = new LinkedHashMap<>();
        List<Zone> wealthyZones = new ArrayList<>();

        public void add(int zoneId, Zone linkedZone) {
            List<Zone> zoneList = zoneMap.get(zoneId);
            if (zoneList == null) {
                zoneList = new ArrayList<>();
            }
            zoneList.add(linkedZone);
            zoneMap.put(zoneId, zoneList);
            if (!wealthyZones.contains(linkedZone) && linkedZone.platinumSource > 0) {
                wealthyZones.add(linkedZone);
                Collections.sort(wealthyZones, Zone.MOST_WEALTHY);
            }
        }

        public boolean hasZone(int zoneId) {
            return zoneMap.get(zoneId) != null;
        }

        public int computeTotalMoney() {
            int money = 0;
            for (List<Zone> zones : zoneMap.values()) {
                for (Zone zone : zones) {
                    money += zone.platinumSource;
                }
            }
            return money;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, List<Zone>> entryZone : zoneMap.entrySet()) {
                sb.append(entryZone.getKey()).append(" -> ");
                for (Zone zone : entryZone.getValue()) {
                    sb.append(zone.zoneId).append(" - ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    private static class Zone {
        public static Comparator<Zone> MOST_WEALTHY = new Comparator<Zone>() {
            @Override
            public int compare(Zone o1, Zone o2) {
                if (o1.platinumSource > o2.platinumSource) {
                    return -1;
                }
                return o1.platinumSource == o2.platinumSource ? 0 : 1;
            }
        };
        int zoneId;
        int platinumSource;
        int ownerId = -1;
        int[] podsP = new int[4];
        List<Zone> linkedZones = new ArrayList<>();

        public Zone(int zoneId, int platinumSource) {
            this.zoneId = zoneId;
            this.platinumSource = platinumSource;
        }

        public Zone(Zone zone) {
            this.zoneId = zone.zoneId;
            this.platinumSource = zone.platinumSource;
            this.ownerId = zone.ownerId;
            this.podsP = Arrays.copyOf(zone.podsP, zone.podsP.length);
        }

        public Zone add(Zone zone) {
            linkedZones.add(zone);
            return this;
        }

        public void initZones(int ownerId, int... podsP) {
            this.ownerId = ownerId;
            this.podsP = podsP;
        }

        public boolean isNeutral() {
            return ownerId == -1;
        }

        public boolean isMyZone() {
            return ownerId == MY_ID;
        }

        public boolean isEnemyZone() {
            return !isMyZone() && !isNeutral();
        }

        public boolean hasEnemy() {
            return isEnemyZone() && podsP[ownerId] > 0;
        }

        public boolean isWealthy() {
            return platinumSource > 0;
        }

        public int allZergs() {
            return podsP[ownerId];
        }

        public boolean hasNearbyEnemy() {
            for (Zone neighbourZone : linkedZones) {
                if (neighbourZone.hasEnemy()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "zoneId = " + zoneId + " - platinumSource = " + platinumSource;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Zone)) return false;

            Zone zone = (Zone) o;

            if (ownerId != zone.ownerId) return false;
            if (platinumSource != zone.platinumSource) return false;
            if (zoneId != zone.zoneId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = zoneId;
            result = 31 * result + platinumSource;
            result = 31 * result + ownerId;
            return result;
        }
    }

    private static class ZoneFactory {
        Map<Integer, Zone> zoneMap;
        List<Integer> wealthyZones;

        private ZoneFactory() {
        }

        public ZoneFactory(Scanner in, int zoneCount) {
            zoneMap = new HashMap<>(zoneCount);
            wealthyZones = new ArrayList<>();
            for (int i = 0; i < zoneCount; i++) {
                int zoneId = in.nextInt(); // this zone's ID (between 0 and zoneCount-1)
                int platinumSource = in.nextInt(); // the amount of Platinum this zone can provide per game turn
                zoneMap.put(zoneId, new Zone(zoneId, platinumSource));
                if (platinumSource > 0) {
                    wealthyZones.add(zoneId);
                }
                in.nextLine();
            }
        }

        public Zone get(int zoneId) {
            return zoneMap.get(zoneId);
        }

        public void initZones(int zoneId, int ownerId, int... podsP) {
            Zone zone = zoneMap.get(zoneId);
            zone.initZones(ownerId, podsP);
        }

        public void link(int zoneId1, int zoneId2) {
            Zone zone1 = zoneMap.get(zoneId1);
            Zone zone2 = zoneMap.get(zoneId2);
            zone1.add(zone2);
            zone2.add(zone1);
        }

        public ZoneFactory copy() {
            ZoneFactory zoneFactory = new ZoneFactory();
            Map<Integer, Zone> zoneMap = new HashMap<>(this.zoneMap.size());
            for (Map.Entry<Integer, Zone> entry : this.zoneMap.entrySet()) {
                zoneMap.put(entry.getKey(), new Zone(entry.getValue()));
            }
            zoneFactory.zoneMap = zoneMap;
            return zoneFactory;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("--------");
            for (Map.Entry<Integer, Zone> entry : zoneMap.entrySet()) {
                sb.append(entry.getKey()).append(" = ").append(entry.getValue().podsP[MY_ID]).append(" / ");
            }
            return sb.toString();
        }
    }

    // -----------------------------------------------------------
    // ZERG
    // -----------------------------------------------------------

    private static class SpawningPool extends ArrayList<Zergs> {
        public static SpawningPool NO_SPAWN = new SpawningPool(0);
        int money;

        public SpawningPool(int money) {
            this.money = money;
        }

        @Override
        public boolean add(Zergs zergs) {
            if (hasEnoughMoney(zergs.nbZergs)) {
                money -= Zergs.UNIT_PRICE * zergs.nbZergs;
                return super.add(zergs);
            }
            return false;
        }

        public int computeNbSpawns() {
            return money / Zergs.UNIT_PRICE;
        }

        public boolean hasEnoughMoney(int nbZergs) {
            return money >= (Zergs.UNIT_PRICE * nbZergs);
        }

        @Override
        public String toString() {
            if (isEmpty()) {
                return "WAIT";
            }
            StringBuilder sb = new StringBuilder();
            for (Zergs zergs : this) {
                sb.append(zergs.toString()).append(" ");
            }
            return sb.toString();
        }
    }

    private static class Zergs {
        static final int UNIT_PRICE = 20;
        int nbZergs;
        int zone;

        private Zergs(int nbZergs) {
            this.nbZergs = nbZergs;
        }

        public static Zergs spawn(int nbZergs) {
            return new Zergs(nbZergs);
        }

        public Zergs in(int zone) {
            this.zone = zone;
            return this;
        }

        @Override
        public String toString() {
            if (nbZergs > 0) {
                return nbZergs + " " + zone;
            }
            return "";
        }
    }

    private static enum ZergAction {
        EXPAND, DEF, ATK
    }

    // -----------------------------------------------------------
    // COMMAND
    // -----------------------------------------------------------

    private static class Command extends ArrayList<Order> {
        public static final Command WAIT = new Command();

        @Override
        public String toString() {
            if (isEmpty()) {
                return "WAIT";
            }
            StringBuilder sb = new StringBuilder();
            for (Order order : this) {
                sb.append(order.toString()).append(" ");
            }
            return sb.toString();
        }
    }

    private static class Order {
        int nbZergs;
        int from;
        int to;

        private Order(int nbZergs) {
            this.nbZergs = nbZergs;
        }

        public static Order order(int nbPOD) {
            return new Order(nbPOD);
        }

        public Order goFrom(int from) {
            this.from = from;
            return this;
        }

        public Order to(int to) {
            this.to = to;
            return this;
        }

        @Override
        public String toString() {
            return nbZergs + " " + from + " " + to;
        }
    }

    // -----------------------------------------------------------
    // INFLUENCE
    // -----------------------------------------------------------

    private static class NeighbourInfluenceMap extends InfluenceMap {
        Zone zone;
        public static NeighbourInfluenceMap from(Zone zone) {
            NeighbourInfluenceMap neighbourInfluenceMap = new NeighbourInfluenceMap();
            neighbourInfluenceMap.zone = zone;

            for (Zone neighbourZone : zone.linkedZones) {
                neighbourInfluenceMap.map.put(neighbourZone.zoneId, influenceMap.getInfluenceOf(neighbourZone.zoneId));
            }
            InfluenceComparator comparator = new InfluenceComparator(neighbourInfluenceMap.map);
            neighbourInfluenceMap.sortedMap = new TreeMap<>(comparator);
            neighbourInfluenceMap.sortedMap.putAll(neighbourInfluenceMap.map);
            return neighbourInfluenceMap;
        }

        public boolean hasAtLeastOneFavorableNeighbour() {
            for (int influence : sortedMap.values()) {
                if (influence > 0) {
                    return true;
                }
                // Since sortedMap is already sorted, if the first one is not > 0, then there are no favorable neighbour
                return false;
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("zone ").append(zone.zoneId).append(" - ");
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                sb.append("(").append(entry.getKey()).append(",").append(entry.getValue()).append(")");
            }
            return sb.toString();
        }
    }

    private static class InfluenceMap {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> sortedMap;
        private static final int[] COEFF = new int[]{3, 2, 1};
        private static final int COEFF_MY_ZONE = 100;

        public static InfluenceMap from(ZoneFactory zoneFactory) {
            InfluenceMap influenceMap = new InfluenceMap();
            for (int zoneId : zoneFactory.zoneMap.keySet()) {
                Zone zone = zoneFactory.get(zoneId);
                init(zone, zone.isMyZone(), zone.platinumSource, influenceMap, 0);
            }
            InfluenceComparator comparator = new InfluenceComparator(influenceMap.map);
            influenceMap.sortedMap = new TreeMap<>(comparator);
            influenceMap.sortedMap.putAll(influenceMap.map);
            return influenceMap;
        }

        public Set<Integer> mostInfluencedZones() {
            return sortedMap.keySet();
        }

        public Integer getInfluenceOf(int zoneId) {
            return map.get(zoneId);
        }

        private static void init(Zone zone, boolean isMyZone, int platinumSource, InfluenceMap influenceMap, int depth) {
            if (depth >= COEFF.length) {
                return;
            }
            Integer influence = influenceMap.map.get(zone.zoneId);
            if (influence == null) {
                influence = 0;
            }
            int value = platinumSource + 1;
            value = isMyZone ? value / 3 : value;
            influence += COEFF[depth] * value;
            if (zone.isMyZone()) {
                influence -= COEFF_MY_ZONE;
            }
            influenceMap.map.put(zone.zoneId, influence);
            for (Zone neighbourZone : zone.linkedZones) {
                init(neighbourZone, isMyZone, platinumSource, influenceMap, depth + 1);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
                sb.append("zoneId = ").append(entry.getKey())
                        .append(" - influence = ").append(entry.getValue())
                        .append("\n");
            }
            return sb.toString();
        }
    }

    private static class InfluenceComparator implements Comparator<Integer> {
        Map<Integer, Integer> map;

        public InfluenceComparator(Map<Integer, Integer> map) {
            this.map = map;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            if (map.get(o1) > map.get(o2)) {
                return -1;
            }
            return 1;
        }
    }
}
