package com.photogallery.photogallery.service;

import com.photogallery.photogallery.model.Publisher;
import com.photogallery.photogallery.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PublisherRepository publisherRepository;

    public void payPublisher(Publisher publisher, float percentage, float price){
        float payment = (percentage/100)*price;
        float revenue = publisher.getRevenue();
        publisher.setRevenue(revenue+payment);
        publisherRepository.save(publisher);
    }

}
