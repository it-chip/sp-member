package com.sp.domain

/**
 * @author Jaedoo Lee
 */
class RequestException(param: String) : CommonException(CommonErrorCode.REQUEST_ERROR, arrayOf(param))
