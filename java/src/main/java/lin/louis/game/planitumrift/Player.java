import java.util.*;
import java.lang.*;
import java.util.Map.Entry;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class Player {
    private static final boolean DEBUG = true;
    private static World world = new World();
    private static ZoneFactory zoneFactory;
    private static int MY_ID = 0;
    private static Random rand = new Random();
    private static int NB_SPAWNS = 0;
    private static boolean isFirstRound = true;

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

        while (true) {
            int money = in.nextInt(); // my available Platinum
            NB_SPAWNS = money / Zergs.UNIT_PRICE;
            in.nextLine();
            Map<Integer, Integer> myZoneList = new HashMap<>();
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
                }
                in.nextLine();
            }

            if (isFirstRound) {
                execute(Command.WAIT.toString());
                execute(spawnZergsForFirstTime(money));
            } else {
                execute(orderZergs(myZoneList));
                execute(spawnZergs(money));
            }

            isFirstRound = false;
        }
    }

    // -----------------------------------------------------------
    // FUNCTIONS
    // -----------------------------------------------------------

    public static <T> void debug(T obj) {
        if (DEBUG) {
            System.err.println(obj.toString());
        }
    }

    public static void execute(String cmd) {
        System.out.println(cmd);
    }

    private static String spawnZergsForFirstTime(int money) {
        SpawningPool spawningPool = new SpawningPool(money);
        // Find the country with the most money
        Collections.sort(world, Continent.MOST_WEALTHY);
        Continent mostWealthyContinent = world.get(0);
        List<Integer> zoneIdList = new ArrayList<>();
        for (Zone zone : mostWealthyContinent.wealthyZones) {
            zoneIdList.add(zone.zoneId);
        }
        int i = 0;
        while (zoneIdList.size() < 5) {
            List<Zone> neighbourZones = mostWealthyContinent.zoneMap.get(zoneIdList.get(i));
            for (Zone neighbourZone : neighbourZones) {
                if (zoneIdList.contains(neighbourZones.get(i).zoneId)) {
                    zoneIdList.add(neighbourZone.zoneId);
                    break;
                }
            }
            i++;
        }
        for (int zoneId : zoneIdList) {
            spawningPool.add(Zergs.spawn(2).in(zoneId));
        }
        return spawningPool.toString();
    }

    private static String orderZergs(Map<Integer, Integer> myZoneList) {
        Command command = new Command();
        for (Map.Entry<Integer, Integer> entryZone : myZoneList.entrySet()) {
            ZergAction zergAction = computeZergAction(entryZone.getKey(), entryZone.getValue());
            switch (zergAction) {
                case DEF:
                    command.addAll(defend(entryZone.getKey(), entryZone.getValue()));
                    break;
                case EXPAND:
                default:
                    command.addAll(divideAndConquer(entryZone.getKey(), entryZone.getValue()));
                    break;
            }
        }
        return command.toString();
    }

    private static List<Order> defend(int zoneId, int nbZergs) {
        List<Order> orderList = new ArrayList<>();
        // TODO: Defend better zones!
        orderList.add(WaitOrder.orderToWait(zoneId));
        return orderList;
    }

    private static ZergAction computeZergAction(int zoneId, int nbZergs) {
        Zone zone = zoneFactory.get(zoneId);
        for (Zone neighbourZone : zone.linkedZones) {
            if (neighbourZone.hasEnemy()) {
                // TODO: Defend only if there are strategic zone
                return ZergAction.DEF;
            }
        }
        // TODO: Implement atk
        return ZergAction.EXPAND;
    }

    private static List<Order> divideAndConquer(int zoneId, int nbZergs) {
        List<Order> orderList = new ArrayList<>();
        int nbZergsLeft = nbZergs;
        Zone zone = zoneFactory.get(zoneId);
        do {
            // FIXME: Go random...
            int index = rand.nextInt(zone.linkedZones.size());
            Zone targetZone = zone.linkedZones.get(index);
            orderList.add(Order.order(1).goFrom(zoneId).to(targetZone.zoneId));
            nbZergsLeft--;
        } while(nbZergsLeft > 0);
        return orderList;
    }

    private static String spawnZergs(int money) {
        SpawningPool spawningPool = new SpawningPool(money);
        // Fetch the remaining zone that are not conquered yet
        List<Zone> neutralWealthyZones = new ArrayList<>();
        for (Zone zone : zoneFactory.zoneMap.values()) {
            if (zone.isNeutral() && zone.planitumSource > 0) {
                neutralWealthyZones.add(zone);
            }
        }
        if (!neutralWealthyZones.isEmpty()) {
            Collections.sort(neutralWealthyZones, Zone.MOST_WEALTHY);
            int nbZergsToSpawn = spawningPool.computeNbSpawns() / 2;
            nbZergsToSpawn = nbZergsToSpawn > neutralWealthyZones.size() ? neutralWealthyZones.size() : nbZergsToSpawn;
            for (int i = 0; i < nbZergsToSpawn; i++) {
                spawningPool.add(Zergs.spawn(1).in(neutralWealthyZones.get(i).zoneId));
            }
        }
        // Defend

        return spawningPool.toString();
    }

    // -----------------------------------------------------------
    // ALGO
    // -----------------------------------------------------------


    // -----------------------------------------------------------
    // ZONE / CONTINENT / WORLD
    // -----------------------------------------------------------

    public static class World extends ArrayList<Continent> {
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

    public static class Continent {
        Map<Integer, List<Zone>> zoneMap = new LinkedHashMap<>();
        List<Zone> wealthyZones = new ArrayList<>();

        public static final Comparator<Continent> MOST_WEALTHY = new Comparator<Continent>() {
            @Override
            public int compare(Continent o1, Continent o2) {
                if (o1.computeTotalMoney() > o2.computeTotalMoney()) {
                    return -1;
                }
                return o1.computeTotalMoney() == o2.computeTotalMoney() ? 0 : 1;
            }
        };

        public void add(int zoneId, Zone linkedZone) {
            List<Zone> zoneList = zoneMap.get(zoneId);
            if (zoneList == null) {
                zoneList = new ArrayList<>();
            }
            zoneList.add(linkedZone);
            zoneMap.put(zoneId, zoneList);
            if (!wealthyZones.contains(linkedZone) && linkedZone.planitumSource > 0) {
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
                    money += zone.planitumSource;
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

    public static class Zone {
        public static Comparator<Zone> MOST_WEALTHY = new Comparator<Zone>() {
            @Override
            public int compare(Zone o1, Zone o2) {
                if (o1.planitumSource > o2.planitumSource) {
                    return -1;
                }
                return o1.planitumSource == o2.planitumSource ? 0 : 1;
            }
        };
        int zoneId;
        int planitumSource;
        int ownerId = -1;
        int[] podsP = new int[4];
        List<Zone> linkedZones = new ArrayList<>();

        public Zone(int zoneId, int planitumSource) {
            this.zoneId = zoneId;
            this.planitumSource = planitumSource;
        }

        public Zone(Zone zone) {
            this.zoneId = zone.zoneId;
            this.planitumSource = zone.planitumSource;
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

        public boolean hasEnemy() {
            return !isMyZone() && !isNeutral() && podsP[ownerId] > 0;
        }

        public boolean isConquering() {
            boolean hasMyZergs = podsP[MY_ID] > 0;
            boolean hasOtherZergs = false;
            for (int i = 0; i < 4; i++) {
                if (i != MY_ID && podsP[i] > 0) {
                    hasOtherZergs = true;
                    break;
                }
            }
            return !isMyZone() && hasMyZergs && !hasOtherZergs;
        }

        @Override
        public String toString() {
            return "zoneId = " + zoneId + " - platinumSource = " + planitumSource;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Zone)) return false;

            Zone zone = (Zone) o;

            if (ownerId != zone.ownerId) return false;
            if (planitumSource != zone.planitumSource) return false;
            if (zoneId != zone.zoneId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = zoneId;
            result = 31 * result + planitumSource;
            result = 31 * result + ownerId;
            return result;
        }
    }

    public static class ZoneFactory {
        Map<Integer, Zone> zoneMap;

        private ZoneFactory() {
        }

        public ZoneFactory(Scanner in, int zoneCount) {
            zoneMap = new HashMap<>(zoneCount);
            for (int i = 0; i < zoneCount; i++) {
                int zoneId = in.nextInt(); // this zone's ID (between 0 and zoneCount-1)
                int platinumSource = in.nextInt(); // the amount of Platinum this zone can provide per game turn
                zoneMap.put(zoneId, new Zone(zoneId, platinumSource));
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

    public static class SpawningPool extends ArrayList<Zergs> {
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

    public static class Zergs {
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
            return nbZergs + " " + zone;
        }
    }

    public static enum ZergAction {
        EXPAND, DEF, ATK
    }

    // -----------------------------------------------------------
    // COMMAND
    // -----------------------------------------------------------

    public static class Command extends ArrayList<Order> {
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

    public static class WaitOrder extends Order {
        private WaitOrder(int nbPOD) {
            super(nbPOD);
        }

        public static WaitOrder orderToWait(int nbPod) {
            return new WaitOrder(nbPod);
        }

        @Override
        public String toString() {
            return "";
        }
    }

    public static class Order implements Comparable<Order> {
        int nbPOD;
        int from;
        int to;

        private Order(int nbPOD) {
            this.nbPOD = nbPOD;
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
            return nbPOD + " " + from + " " + to;
        }

        @Override
        public int compareTo(Order o) {
            return 0;
        }
    }
}
