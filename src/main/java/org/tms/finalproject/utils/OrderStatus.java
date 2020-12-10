package org.tms.finalproject.utils;

public enum OrderStatus {
    ACTIVE_STATUS {
        @Override
        public String getStatus() {
            return "ACTIVE_STATUS";
        }

        @Override
        public String toString() {
            return "Order is active, worker can take it";
        }
    },

    AWAITING_APPROVAL_STATUS {
        @Override
        public String getStatus() {
            return "AWAITING_APPROVAL_STATUS";
        }

        @Override
        public String toString() {
            return "Order was taken some workers, and expects customer's approve";
        }
    },

    IN_WORK_STATUS{
        @Override
        public String getStatus() {
            return "IN_WORK_STATUS";
        }

        @Override
        public String toString() {
            return "Order is in work";
        }
    },

    CLOSED_STATUS{
        @Override
        public String getStatus() {
            return "CLOSED_STATUS";
        }

        @Override
        public String toString() {
            return "Order has closed";
        }
    };

    public abstract String getStatus();
}
