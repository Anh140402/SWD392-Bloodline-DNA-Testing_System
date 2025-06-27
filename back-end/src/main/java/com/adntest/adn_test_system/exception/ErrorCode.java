package com.adntest.adn_test_system.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Authentication & Authorization Errors (4xx)
    INVALID_CREDENTIALS("AUTH_001", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS("AUTH_002", "Access denied. Insufficient permissions", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("AUTH_003", "Authentication token has expired", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("AUTH_004", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("AUTH_005", "User not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("AUTH_006", "User already exists", HttpStatus.CONFLICT),
    ACCOUNT_DISABLED("AUTH_007", "User account is disabled", HttpStatus.FORBIDDEN),
    PASSWORD_RESET_REQUIRED("AUTH_008", "Password reset required", HttpStatus.FORBIDDEN),

    // Account Management Errors (4xx)
    ACCOUNT_NOT_FOUND("ACC_001", "Account not found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS("ACC_002", "Username already exists", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("ACC_003", "Email already exists", HttpStatus.CONFLICT),
    INVALID_EMAIL_FORMAT("ACC_004", "Invalid email format", HttpStatus.BAD_REQUEST),
    WEAK_PASSWORD("ACC_005", "Password does not meet security requirements", HttpStatus.BAD_REQUEST),
    PROFILE_UPDATE_FAILED("ACC_006", "Failed to update profile", HttpStatus.BAD_REQUEST),

    // Service Management Errors (4xx)
    SERVICE_NOT_FOUND("SRV_001", "Service not found", HttpStatus.NOT_FOUND),
    SERVICE_NAME_EXISTS("SRV_002", "Service with this name already exists", HttpStatus.CONFLICT),
    INVALID_SERVICE_TYPE("SRV_003", "Invalid service type", HttpStatus.BAD_REQUEST),
    SERVICE_PRICE_INVALID("SRV_004", "Invalid service price", HttpStatus.BAD_REQUEST),
    SERVICE_UNAVAILABLE("SRV_005", "Service is currently unavailable", HttpStatus.SERVICE_UNAVAILABLE),

    // Test Order Errors (4xx)
    TEST_ORDER_NOT_FOUND("ORD_001", "Test order not found", HttpStatus.NOT_FOUND),
    INVALID_ORDER_STATUS("ORD_002", "Invalid order status", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_TYPE("ORD_003", "Invalid order type", HttpStatus.BAD_REQUEST),
    ORDER_CANNOT_BE_MODIFIED("ORD_004", "Order cannot be modified in current status", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_CANCELLED("ORD_005", "Order is already cancelled", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_COMPLETED("ORD_006", "Order is already completed", HttpStatus.BAD_REQUEST),
    PREFERRED_DATE_INVALID("ORD_007", "Preferred date must be in the future", HttpStatus.BAD_REQUEST),
    ORDER_DELETION_NOT_ALLOWED("ORD_008", "Order cannot be deleted in current status", HttpStatus.BAD_REQUEST),

    // Sample Management Errors (4xx)
    SAMPLE_NOT_FOUND("SMP_001", "Sample not found", HttpStatus.NOT_FOUND),
    INVALID_SAMPLE_TYPE("SMP_002", "Invalid sample type", HttpStatus.BAD_REQUEST),
    SAMPLE_TYPE_REQUIRED("SMP_003", "Sample type is required", HttpStatus.BAD_REQUEST),
    SAMPLE_ALREADY_PROCESSED("SMP_004", "Sample has already been processed", HttpStatus.BAD_REQUEST),
    SAMPLE_CONTAMINATED("SMP_005", "Sample is contaminated and cannot be processed", HttpStatus.BAD_REQUEST),

    // Kit Management Errors (4xx)
    KIT_NOT_FOUND("KIT_001", "Kit not found", HttpStatus.NOT_FOUND),
    KIT_CODE_EXISTS("KIT_002", "Kit with this code already exists", HttpStatus.CONFLICT),
    KIT_ALREADY_ISSUED("KIT_003", "Kit already exists for this test order", HttpStatus.CONFLICT),
    KIT_CODE_INVALID("KIT_004", "Invalid kit code format", HttpStatus.BAD_REQUEST),
    KIT_EXPIRED("KIT_005", "Kit has expired", HttpStatus.BAD_REQUEST),
    KIT_ALREADY_USED("KIT_006", "Kit has already been used", HttpStatus.BAD_REQUEST),

    // Appointment Management Errors (4xx)
    APPOINTMENT_NOT_FOUND("APT_001", "Appointment not found", HttpStatus.NOT_FOUND),
    APPOINTMENT_CONFLICT("APT_002", "Appointment time conflicts with existing appointment", HttpStatus.CONFLICT),
    APPOINTMENT_IN_PAST("APT_003", "Cannot schedule appointment in the past", HttpStatus.BAD_REQUEST),
    APPOINTMENT_TOO_SOON("APT_004", "Appointment must be scheduled at least 24 hours in advance", HttpStatus.BAD_REQUEST),
    APPOINTMENT_ALREADY_CONFIRMED("APT_005", "Appointment is already confirmed", HttpStatus.BAD_REQUEST),
    APPOINTMENT_CANCELLED("APT_006", "Appointment has been cancelled", HttpStatus.BAD_REQUEST),
    INVALID_APPOINTMENT_TIME("APT_007", "Invalid appointment time", HttpStatus.BAD_REQUEST),

    // Test Report Errors (4xx)
    TEST_REPORT_NOT_FOUND("RPT_001", "Test report not found", HttpStatus.NOT_FOUND),
    REPORT_ALREADY_EXISTS("RPT_002", "Test report already exists for this order", HttpStatus.CONFLICT),
    INVALID_TEST_RESULT("RPT_003", "Invalid test result format", HttpStatus.BAD_REQUEST),
    RESULT_STATUS_REQUIRED("RPT_004", "Test result status (positive/negative) is required", HttpStatus.BAD_REQUEST),
    REPORT_CANNOT_BE_CREATED("RPT_005", "Cannot create report for order in current status", HttpStatus.BAD_REQUEST),
    REPORT_GENERATION_FAILED("RPT_006", "Failed to generate test report", HttpStatus.INTERNAL_SERVER_ERROR),

    // Feedback Management Errors (4xx)
    FEEDBACK_NOT_FOUND("FBK_001", "Feedback not found", HttpStatus.NOT_FOUND),
    INVALID_RATING("FBK_002", "Rating must be between 1 and 5", HttpStatus.BAD_REQUEST),
    FEEDBACK_ALREADY_EXISTS("FBK_003", "Feedback already exists for this order", HttpStatus.CONFLICT),
    FEEDBACK_NOT_ALLOWED("FBK_004", "Feedback can only be provided for completed orders", HttpStatus.BAD_REQUEST),

    // Blog Management Errors (4xx)
    BLOG_NOT_FOUND("BLG_001", "Blog post not found", HttpStatus.NOT_FOUND),
    BLOG_TITLE_REQUIRED("BLG_002", "Blog title is required", HttpStatus.BAD_REQUEST),
    BLOG_CONTENT_REQUIRED("BLG_003", "Blog content is required", HttpStatus.BAD_REQUEST),
    BLOG_TITLE_TOO_LONG("BLG_004", "Blog title is too long", HttpStatus.BAD_REQUEST),
    BLOG_ACCESS_DENIED("BLG_005", "Access denied to modify this blog post", HttpStatus.FORBIDDEN),

    // Validation Errors (4xx)
    REQUIRED_FIELD_MISSING("VAL_001", "Required field is missing", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_FORMAT("VAL_002", "Invalid input format", HttpStatus.BAD_REQUEST),
    INPUT_TOO_LONG("VAL_003", "Input exceeds maximum length", HttpStatus.BAD_REQUEST),
    INPUT_TOO_SHORT("VAL_004", "Input is below minimum length", HttpStatus.BAD_REQUEST),
    INVALID_DATE_RANGE("VAL_005", "Invalid date range", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER("VAL_006", "Invalid phone number format", HttpStatus.BAD_REQUEST),
    INVALID_ID_FORMAT("VAL_007", "Invalid ID format", HttpStatus.BAD_REQUEST),

    // System Errors (5xx)
    INTERNAL_SERVER_ERROR("SYS_001", "Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_CONNECTION_ERROR("SYS_002", "Database connection error", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_TRANSACTION_FAILED("SYS_003", "Database transaction failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_PROCESSING_ERROR("SYS_004", "File processing error", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_SERVICE_ERROR("SYS_005", "Email service error", HttpStatus.INTERNAL_SERVER_ERROR),
    EXTERNAL_SERVICE_ERROR("SYS_006", "External service error", HttpStatus.BAD_GATEWAY),
    CONFIGURATION_ERROR("SYS_007", "System configuration error", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_EXHAUSTED("SYS_008", "System resources exhausted", HttpStatus.SERVICE_UNAVAILABLE),

    // Business Logic Errors (4xx)
    BUSINESS_RULE_VIOLATION("BIZ_001", "Business rule violation", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_PERMISSIONS("BIZ_002", "Insufficient permissions for this operation", HttpStatus.FORBIDDEN),
    OPERATION_NOT_SUPPORTED("BIZ_003", "Operation not supported in current context", HttpStatus.BAD_REQUEST),
    RESOURCE_LOCKED("BIZ_004", "Resource is currently locked", HttpStatus.CONFLICT),
    QUOTA_EXCEEDED("BIZ_005", "Operation quota exceeded", HttpStatus.TOO_MANY_REQUESTS),
    DATA_INTEGRITY_VIOLATION("BIZ_006", "Data integrity constraint violation", HttpStatus.CONFLICT),

    // Generic Errors
    UNKNOWN_ERROR("GEN_001", "An unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    OPERATION_FAILED("GEN_002", "Operation failed", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("GEN_003", "Requested resource not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST("GEN_004", "Invalid request", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getFormattedMessage() {
        return String.format("[%s] %s", code, message);
    }

    /**
     * Creates a formatted error message with custom details
     * @param details Additional details to append to the error message
     * @return Formatted error message with details
     */
    public String getFormattedMessage(String details) {
        return String.format("[%s] %s: %s", code, message, details);
    }

    /**
     * Checks if this error code represents a client error (4xx)
     * @return true if this is a client error, false otherwise
     */
    public boolean isClientError() {
        return httpStatus.is4xxClientError();
    }

    /**
     * Checks if this error code represents a server error (5xx)
     * @return true if this is a server error, false otherwise
     */
    public boolean isServerError() {
        return httpStatus.is5xxServerError();
    }

    /**
     * Finds an ErrorCode by its code string
     * @param code The error code string to search for
     * @return The matching ErrorCode, or UNKNOWN_ERROR if not found
     */
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return UNKNOWN_ERROR;
    }
}

