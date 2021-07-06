package pocs.rabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Queue {

    public final static String FILA_PROCESSAMENTO = "tarefa.processamento";
    public final static String FILA_SOLICITACAO = "cliente.solicitacao";

    private ConnectionFactory getFactory() {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "localhost" );
        factory.setPort( 5672 );
        factory.setUsername( "guest" );
        factory.setPassword( "guest" );
        return factory;
    }

    public void enviar(
        final Long nroProcesso,
        final String fila
    ) {
        String json = String.format( "{ \"nroProcesso\": %s }", nroProcesso );
        try (
            Connection connection = getFactory().newConnection();
            Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare( fila , false,false,false, null);
            channel.basicPublish(
                "",
                fila,
                PERSISTENT_TEXT_PLAIN,
                json.getBytes( UTF_8 )
            );
        } catch ( IOException | TimeoutException e ) {
            e.printStackTrace();
        }
    }
}
