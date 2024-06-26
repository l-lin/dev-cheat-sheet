package lin.louis.logging_with_aop.pet;

public record Pet(Id id, Name name) {

    public record Id(String value) {

        public Id {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Pet id is mandatory.");
            }
        }
    }

    public record Name(String value) {

        public Name {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Pet name is mandatory.");
            }
        }
    }
}
