package com.kanojiya.studentcourse.custom.validators;


import com.kanojiya.studentcourse.custom.annotations.ValidMobileNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidator
        implements ConstraintValidator<ValidMobileNumber, String> {

    @Override
    public boolean isValid(String mobile,
                           ConstraintValidatorContext context) {

        // ❌ Null ya empty check
        if (mobile == null || mobile.isEmpty()) {
            return false;
        }

        // 1️⃣ Only digits & length = 10
        if (!mobile.matches("\\d{10}")) {
            return false;
        }

        // 2️⃣ First digit nikal lo
        int firstDigit = Character.getNumericValue(mobile.charAt(0));

        // 3️⃣ Starting digit between 6 to 9
        if (firstDigit < 6 || firstDigit > 9) {
            return false;
        }

        // 4️⃣ Starting digit EVEN hona chahiye
        if (firstDigit % 2 != 0) {
            return false;
        }

        return true; // ✅ All conditions passed
    }
}
