package org.blog.knowyourtrade.validation;

import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.domain.dto.request.PostRequest;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ParameterException;
import org.blog.knowyourtrade.util.StringUtils;

@Slf4j
public class RequestValidate {

    public static void validateRequest(PostRequest postRequest) {
        validateTitle(postRequest.getTitle());
        validateContent(postRequest.getContent());
        validateCategor(postRequest.getCategory());
    }

    private static void validateCategor(String category) {
        boolean result = checkRequestEmpty(category);

        if (!result) {
            throw new ParameterException("Category cannot be empty", ErrorCode.PARAM_ILLEGAL);
        }
    }

    private static void validateContent(String content) {
        boolean result = checkRequestEmpty(content);

        if (!result) {
            throw new ParameterException("Content cannot be empty", ErrorCode.PARAM_ILLEGAL);
        }
    }

    private static void validateTitle(String title) {
        boolean result = checkRequestEmpty(title);

        if (!result) {
            throw new ParameterException("Title cannot be empty", ErrorCode.PARAM_ILLEGAL);
        }
    }

    public static void validatePostId(String value) {
        boolean result = checkRequestEmpty(value);

        if (!result) {
            throw new ParameterException("Post Id cannot be empty", ErrorCode.PARAM_ILLEGAL);
        }
    }

    private static boolean checkRequestEmpty(String value) {
        return StringUtils.checkIsEmpty(value);
    }
}
