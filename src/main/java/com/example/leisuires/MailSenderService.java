package com.example.leisuires;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;

@Service
public class MailSenderService implements MailSenderInt {
    @Value("${mailClientId}")
    private String mId;
    @Value("${mailTenantId}")
    private String mTId;
    @Value("${clientSecret}")
    private String secret;

    // Azure AD app credentials
        String clientId = mId;
        String tenantId = mTId;
        String clientSecret =secret; 
    @Override
    public void sendEmail(String name, String senderMail, String subject, String userMessage) { 
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .tenantId(tenantId)
            .clientSecret(clientSecret)
            .build();

        TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(credential);

        GraphServiceClient<?> graphClient =
            GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();

        // Build message
        Message message = new Message();
        message.subject = subject;

        ItemBody body = new ItemBody();
        body.contentType = BodyType.TEXT;
        body.content = "From: " + name + " (" + "testuser1@mvraolive.onmicrosoft.com" + ")\n\n" + userMessage;
        message.body = body;

        Recipient recipient = new Recipient();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "maheshpolineni032@gmail.com"; // destination mailbox
        recipient.emailAddress = emailAddress;
        message.toRecipients = java.util.Arrays.asList(recipient);

        // ✅ Wrap message in UserSendMailParameterSet
        UserSendMailParameterSet sendMailParameters = 
            UserSendMailParameterSet.newBuilder()
                .withMessage(message)
                .withSaveToSentItems(false) // optional
                .build();

        // Send mail
        graphClient.users("testuser1@mvraolive.onmicrosoft.com")
            .sendMail(sendMailParameters)
            .buildRequest()
            .post();

        System.out.println("Email sent successfully via Graph API!");
    }
}