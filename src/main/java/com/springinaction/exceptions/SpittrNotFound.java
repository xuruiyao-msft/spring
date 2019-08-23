package com.springinaction.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "spittr not found")
public class SpittrNotFound extends RuntimeException {
}
