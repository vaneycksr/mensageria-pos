import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {

    @Override
    public void run() {
        try { // Create a connection factory.
            ActiveMQConnectionFactory factory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);

            //Create connection.
            Connection connection = factory.createConnection();
// Start the connection
            connection.start();
            // Cria a sessão
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Crea a fila e informa qual o destinatário.
            Destination queue = session.createQueue("br.paciente");
            MessageConsumer consumer = session.createConsumer(queue);
            //Message message = consumer.receive();
            Message message = consumer.receive();
            if (message instanceof ObjectMessage) { // comparava se era instancia de TextMessage
                ObjectMessage objectMessage = (ObjectMessage) message;
                //String text = textMessage.getText();
                Paciente p = (Paciente) objectMessage.getObject();
                System.out.println("Consumer Received: " + p.getNome());
            }
            session.close();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println("Exception Occured");
        }
    }
}