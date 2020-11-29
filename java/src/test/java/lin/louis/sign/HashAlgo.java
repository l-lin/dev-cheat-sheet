package lin.louis.sign;

public enum HashAlgo {
    SHA256("SHA-256");

    private final String name;

    HashAlgo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
