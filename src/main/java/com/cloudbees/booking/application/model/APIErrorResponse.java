package com.cloudbees.booking.application.model;

import java.util.List;

public record APIErrorResponse(List<String> messages) {
}
