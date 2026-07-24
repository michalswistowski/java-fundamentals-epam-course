package com.epam.training.person.persistence;

import com.epam.training.person.domain.Phonebook;
import com.epam.training.person.domain.Subscriber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.Marshaller;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;

public class XMLSubscriberWriter implements DataWriter<List<Subscriber>> {

    private final OutputStreamWriter outputWriter;

    public XMLSubscriberWriter(OutputStream out) {
        this.outputWriter = new OutputStreamWriter(out);
    }

    @Override
    public void write(List<Subscriber> value) {

        try {

            JAXBContext context = JAXBContext.newInstance(Phonebook.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Phonebook phonebook = new Phonebook();
            phonebook.setSubscriberList(value);

            marshaller.marshal(phonebook, outputWriter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        outputWriter.close();
    }
}
