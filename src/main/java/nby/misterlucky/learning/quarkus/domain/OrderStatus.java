package nby.misterlucky.learning.quarkus.domain;

import nby.misterlucky.learning.quarkus.domain.entity.OrderStatusException;

public enum OrderStatus {

    JUST_REQUESTED(1),
    IN_PROGRESS(2),
    READY_FOR_DELIVERING(3),
    PASSED_TO_DELIVERING_SERVICE(4),
    DELIVERED(5);
    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus byCode(int code) {
        switch (code) {
            case 1:
                return JUST_REQUESTED;
            case 2:
                return IN_PROGRESS;
            case 3:
                return READY_FOR_DELIVERING;
            case 4:
                return PASSED_TO_DELIVERING_SERVICE;
            case 5:
                return DELIVERED;
            default:
                throw new OrderStatusException(code);
        }
    }
}
