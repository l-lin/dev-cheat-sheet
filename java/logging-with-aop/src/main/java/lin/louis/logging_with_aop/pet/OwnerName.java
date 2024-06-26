package lin.louis.logging_with_aop.pet;

public record OwnerName(String value) {

    public OwnerName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Owner name is mandatory.");
        }
    }
}
