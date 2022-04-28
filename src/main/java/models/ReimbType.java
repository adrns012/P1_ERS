package models;

public enum ReimbType {
    Travel(1) {
        @Override
        public String toString() {
            return "Travel";
        }
    },

    Lodging(2) {
        @Override
        public String toString() {
            return "Lodging";
        }
    },

    Food(3) {
        @Override
        public String toString() {
            return "Food";
        }
    },

    Other(4) {
        @Override
        public String toString() {
            return "Other";
        }
    };

    private Integer reimbType;

    ReimbType(Integer reimbType) {
        this.reimbType = reimbType;
    }

    public Integer getReimbType() {
        return reimbType;
    }
}