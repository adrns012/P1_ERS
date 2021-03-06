package models;

public enum ReimbStatus {
    PENDING(1) {
        @Override
        public String toString() {
            return "Pending";
        }
    },

    APPROVED(2) {
        @Override
        public String toString() {
            return "Approved";
        }
    },

    DENIED(3) {
        @Override
        public String toString() {
            return "Denied";
        }
    };

    private Integer reimbStatus;

    ReimbStatus(Integer reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public Integer getReimbStatus() {
        return reimbStatus;
    }
}