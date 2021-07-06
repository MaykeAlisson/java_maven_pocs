package pocs.rabbitMq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Queue {

    public final static String ROUTING_KEY_B2B_PRE_CADASTRO = "cliente.pre-cadastro";
    public final static String ROUTING_KEY_B2B_SOLICITACAO_SEGUNDA_VIA = "cliente.solicitacao-segunda-via";

    private ConnectionFactory getFactory() {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost( "localhost" );
        factory.setVirtualHost("b2b");
        factory.setPort( 5672 );
        factory.setUsername( "guest" );
        factory.setPassword( "guest" );
        return factory;
    }

    public void enviar(
        final String exchange,
        final Long nroProcesso,
        final String routingKey
    ) {
        String json = String.format( "{ \"nroProcesso\": %s }", nroProcesso );
        AMQP.BasicProperties properties = (new AMQP.BasicProperties()).builder().contentType("application/json").deliveryMode(2).build();
        try (
            Connection connection = getFactory().newConnection();
            Channel channel = connection.createChannel()
        ) {
            // channel.basicPublish(exchange, routingKey, properties, mensagem);
            channel.queueDeclare( routingKey , false,false,false, null);
            channel.basicPublish(exchange, routingKey, properties, json.getBytes( UTF_8 ));
        } catch ( IOException | TimeoutException e ) {
            System.out.println("ERROR______");
            e.printStackTrace();
        }
    }
}
