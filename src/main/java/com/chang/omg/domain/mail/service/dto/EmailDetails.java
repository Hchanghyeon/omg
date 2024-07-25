package com.chang.omg.domain.mail.service.dto;

import lombok.Builder;

@Builder
public record EmailDetails(String subject, String message, String receiver) {

}
