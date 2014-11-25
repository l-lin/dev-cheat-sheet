import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

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


        // game loop
        while (true) {
//            debug(world);

            int money = in.nextInt(); // my available Platinum
            NB_SPAWNS = money / Zerg.UNIT_PRICE;
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

        public void add(int zoneId, Zone linkedZone) {
            List<Zone> zoneList = zoneMap.get(zoneId);
            if (zoneList == null) {
                zoneList = new ArrayList<>();
            }
            zoneList.add(linkedZone);
            zoneMap.put(zoneId, zoneList);
        }

        public boolean hasZone(int zoneId) {
            return zoneMap.get(zoneId) != null;
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
        int zoneId;
        int planitumSource;
        int distance = 1;
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

    public static class Zerg {
        static final int UNIT_PRICE = 20;
    }

    // -----------------------------------------------------------
    // HIVE
    // -----------------------------------------------------------

    public static class Hive extends ArrayList<SpawningPool> {
        public Hive() {
        }

        @Override
        public String toString() {
            if (isEmpty()) {
                return "WAIT";
            }
            StringBuilder sb = new StringBuilder();
            for (SpawningPool spawningPool : this) {
                sb.append(spawningPool.toString()).append(" ");
            }
            return sb.toString();
        }
    }

    public static class SpawningPool {
        int nbZergs;
        int zone;

        private SpawningPool(int nbZergs) {
            this.nbZergs = nbZergs;
        }

        public static SpawningPool spawn(int nbZergs) {
            return new SpawningPool(nbZergs);
        }

        public SpawningPool in(int zone) {
            this.zone = zone;
            return this;
        }

        @Override
        public String toString() {
            return nbZergs + " " + zone;
        }
    }

    // -----------------------------------------------------------
    // COMMAND
    // -----------------------------------------------------------

    public static class Command extends ArrayList<Order> {
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
