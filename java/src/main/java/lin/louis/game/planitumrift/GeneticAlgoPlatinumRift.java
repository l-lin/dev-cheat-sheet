package lin.louis.game.planitumrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 */
class GeneticAlgoPlatinumRift {
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

            Hive possibleHive = buildPossibleHive();

            Population population = new Population(possibleHive, 10, myZoneList);
            Chromosome chromosome = population.run();
            System.out.println(chromosome.command.toString());
            System.out.println(chromosome.hive.toString());
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

    public static Hive buildPossibleHive() {
        Hive hive = new Hive();
        for (Zone zone : zoneFactory.zoneMap.values()) {
            if (zone.isNeutral() || zone.isMyZone()) {
                hive.add(SpawningPool.spawn(1).in(zone.zoneId));
            }
        }
        return hive;
    }

    // -----------------------------------------------------------
    // GENETIC ALGO
    // -----------------------------------------------------------

    public static class Chromosome implements Comparable<Chromosome> {
        private static final float MUTATION_RATE = 0.5f;
        Command command;
        Hive hive;
        private int score = 0;

        private Chromosome(Command command, Hive hive) {
            this.command = command;
            this.hive = hive;
        }

        public static Chromosome random(Hive possibleHive, Map<Integer, Integer> myZoneList) {
            Command command = new Command();
            for (Map.Entry<Integer, Integer> myZoneEntry : myZoneList.entrySet()) {
                Zone zone = zoneFactory.get(myZoneEntry.getKey());
                int nbZergs = myZoneEntry.getValue();
                if (zone.linkedZones.isEmpty()) {
                    command.add(WaitOrder.orderToWait(1));
                } else {
                    for (int i = 0; i < nbZergs; i++) {
                        int index = rand.nextInt(zone.linkedZones.size() + 1);
                        if (index >= zone.linkedZones.size()) {
                            command.add(WaitOrder.orderToWait(1));
                        } else {
                            Zone targetZone = zone.linkedZones.get(index);
                            command.add(Order.order(1).goFrom(zone.zoneId).to(targetZone.zoneId));
                        }
                    }
                }
            }

            Hive hive = new Hive();
            List<Integer> usedIndex = new ArrayList<>(possibleHive.size());
            for (int i = 0; i < NB_SPAWNS; i++) {
                int j = 0;
                int index;
                do {
                    index = rand.nextInt(possibleHive.size());
                    j++;
                } while(usedIndex.contains(index) && j < 10);
                hive.add(possibleHive.get(index));
                usedIndex.add(index);
            }
            return new Chromosome(command, hive);
        }

        public int computeFitness() {
            ZoneFactory zoneFactoryTmp = zoneFactory.copy();
            for (Order order : command) {
                Zone zoneFrom = zoneFactoryTmp.get(order.from);
                zoneFrom.podsP[MY_ID] -= order.nbPOD;
                Zone zoneTo = zoneFactoryTmp.get(order.to);
                zoneTo.podsP[MY_ID] += order.nbPOD;
            }
            for (SpawningPool spawningPool : hive) {
                Zone zone = zoneFactoryTmp.get(spawningPool.zone);
                zone.podsP[MY_ID] += spawningPool.nbZergs;
            }
            score = 0;
            for (Map.Entry<Integer, Zone> zoneEntry : zoneFactoryTmp.zoneMap.entrySet()) {
                Zone zone = zoneEntry.getValue();
                // FIXME: Take into account the nb of zergs
                score += (zone.isMyZone() || zone.isConquering()) ? zone.planitumSource :
                        zone.isNeutral() ? 0 : -zone.planitumSource;
//                        zone.isNeutral() ? 0 : -(zone.planitumSource * 0.2 * zone.podsP[zone.ownerId]);
            }

            return score;
        }

        public Chromosome[] mateWith(Chromosome chromosome) {
            Command commandChild1 = new Command();
            Command commandChild2 = new Command();
            if (chromosome.command.size() > 0) {
                int commandRandomIndex = rand.nextInt(this.command.size());
                commandChild1.addAll(this.command.subList(0, commandRandomIndex));
                commandChild1.addAll(chromosome.command.subList(commandRandomIndex, chromosome.command.size()));
                commandChild2.addAll(chromosome.command.subList(0, commandRandomIndex));
                commandChild2.addAll(this.command.subList(commandRandomIndex, this.command.size()));
            }
            Hive hiveChild1 = new Hive();
            Hive hiveChild2 = new Hive();
            if (chromosome.hive.size() > 0) {
                int hiveRandomIndex = rand.nextInt(this.hive.size());
                hiveChild1.addAll(this.hive.subList(0, hiveRandomIndex));
                hiveChild1.addAll(chromosome.hive.subList(hiveRandomIndex, chromosome.hive.size()));
                hiveChild2.addAll(chromosome.hive.subList(0, hiveRandomIndex));
                hiveChild2.addAll(this.hive.subList(hiveRandomIndex, this.hive.size()));
            }

            return new Chromosome[]{
                    new Chromosome(commandChild1, hiveChild1),
                    new Chromosome(commandChild2, hiveChild2)
            };
        }

        public void mutate(Hive possibleHive) {
            if (rand.nextDouble() > MUTATION_RATE) {
                return;
            }

            if (this.command.size() > 0) {
                int commandRandomIndex = rand.nextInt(this.command.size());
                Order order = this.command.get(commandRandomIndex);
                Zone zone = zoneFactory.get(order.from);
                if (zone.linkedZones.isEmpty()) {
                    command.set(commandRandomIndex, WaitOrder.orderToWait(1));
                } else {
                    int index = rand.nextInt(zone.linkedZones.size() + 1);
                    if (index >= zone.linkedZones.size()) {
                        command.set(commandRandomIndex, WaitOrder.orderToWait(1));
                    } else {
                        Zone targetZone = zone.linkedZones.get(index);
                        command.set(commandRandomIndex, Order.order(1).goFrom(zone.zoneId).to(targetZone.zoneId));
                    }
                }
            }

            if (this.hive.size() > 0) {
                int hiveRandomIndex = rand.nextInt(this.hive.size());
                int hiveRandomIndex2;
                int j = 0;
                do {
                    hiveRandomIndex2 = rand.nextInt(this.hive.size());
                    j++;
                } while (hiveRandomIndex == hiveRandomIndex2 && j < 10);
                this.hive.set(hiveRandomIndex, possibleHive.get(hiveRandomIndex2));
            }
        }

        @Override
        public int compareTo(Chromosome chromosome) {
            return chromosome.score - this.score;
        }
    }

    public static class Population {
        private static int threshold = 15;
        private static float ELITISM = 0.2f;
        List<Chromosome> chromosomeList;
        private int noImprovement = 0;
        private int lastScore = 0;
        private int size;
        private int generationNumber;

        private Hive possibleHive;
        private Map<Integer, Integer> myZoneList;

        public Population(Hive possibleHive, int size, Map<Integer, Integer> myZoneList) {
            this.possibleHive = possibleHive;
            this.size = size;
            this.myZoneList = myZoneList;
            chromosomeList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                chromosomeList.add(Chromosome.random(possibleHive, myZoneList));
            }
        }

        public Chromosome run() {
            generation();
            if (generationNumber < threshold) {
                generationNumber++;
                return run();
            }
            debug(chromosomeList.size());
            debug(currentBest().score);
            debug(chromosomeList.get(chromosomeList.size() - 1).score);
            return currentBest();
//            if (noImprovement < threshold) {
//                lastScore = currentBest().score;
//                generation();
//                if (lastScore > currentBest().score) {
//                    noImprovement++;
//                } else {
//                    noImprovement = 0;
//                }
//                return run();
//            }
//            return currentBest();
        }

        private void generation() {
            sort();
            kill();
            mate();
            fill();
            sort();
        }

        private void sort() {
            for (Chromosome chromosome : chromosomeList) {
                chromosome.computeFitness();
            }
            Collections.sort(chromosomeList);
        }

        private void kill() {
            int target = (int) Math.floor(ELITISM * chromosomeList.size());
            while (chromosomeList.size() > target) {
                chromosomeList.remove(chromosomeList.size() - 1);
            }
        }

        private void mate() {
            int index1 = rand.nextInt(chromosomeList.size());
            int index2;
            do {
                index2 = rand.nextInt(chromosomeList.size());
            } while (index1 == index2);
            Chromosome[] children = chromosomeList.get(index1).mateWith(chromosomeList.get(index2));
            children[0].mutate(possibleHive);
            children[1].mutate(possibleHive);
            chromosomeList.add(children[0]);
            chromosomeList.add(children[1]);
        }

        private void fill() {
            while (chromosomeList.size() < size) {
                if (chromosomeList.size() < size / 3) {
                    chromosomeList.add(Chromosome.random(possibleHive, myZoneList));
                } else {
                    mate();
                }
            }
        }

        private Chromosome currentBest() {
            return chromosomeList.get(0);
        }
    }


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
