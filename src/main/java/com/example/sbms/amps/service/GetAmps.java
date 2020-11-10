/*
 * MIT License
 *
 * Copyright (c) 2020 Rarysoft Enterprises
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.sbms.amps.service;

import com.example.sbms.amps.model.Amp;
import com.example.sbms.amps.model.Amps;
import com.example.sbms.amps.model.Filter;
import com.example.sbms.amps.service.data.AmpRepository;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableKafka
public class GetAmps {
    private final AmpRepository ampRepository;

    public GetAmps(AmpRepository ampRepository) {
        this.ampRepository = ampRepository;
    }

    public List<Amp> allByMakeAndModel() {
        return ampRepository.findAll(Sort.by("make", "model"));
    }

    @KafkaListener(topics = "${spring.kafka.consumer.properties.event.amps-requested.topic}", containerFactory = "filterKafkaListenerContainerFactory")
    @SendTo
    public Amps receiveGuitarsRequest(@Payload Filter filter) {
        if (filter.forAll()) {
            return new Amps(ampRepository.findAll(Sort.by("make", "model")));
        }
        if (filter.forId()) {
            Optional<Amp> amp = ampRepository.findById(filter.id());
            List<Amp> amps = new ArrayList<>();
            amp.ifPresent(amps::add);
            return new Amps(amps);
        }
        // TODO: handle other filters
        return new Amps();
    }
}
