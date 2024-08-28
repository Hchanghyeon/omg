package com.chang.omg.domain.mail.service;

import com.chang.omg.domain.mail.service.dto.EmailDetails;

public interface MailService {

    void sendEmail(final EmailDetails emailDetails);

}
