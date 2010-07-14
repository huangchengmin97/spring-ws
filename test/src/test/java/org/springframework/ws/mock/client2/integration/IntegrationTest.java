/*
 * Copyright 2005-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.mock.client2.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.springframework.ws.mock.client2.WebServiceMock.*;

/**
 * Integration test for client-side WebService testing. In different package so we can't use the package-protected
 * classes.
 *
 * @author Arjen Poutsma
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("integration-test.xml")
public class IntegrationTest {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Before
    public void setUpMocks() throws Exception {
        mockWebServiceTemplate(webServiceTemplate);
    }

    @Test
    public void basic() throws Exception {
        String expectedRequestPayload = "<customerCountRequest xmlns='http://springframework.org/client'>" +
                "<customerName>John Doe</customerName>" + "</customerCountRequest>";
        String responsePayload = "<customerCountResponse xmlns='http://springframework.org/client'>" +
                "<customerCount>10</customerCount>" + "</customerCountResponse>";

        expect(payload(expectedRequestPayload)).andRespond(withPayload(responsePayload));

        CustomerCountRequest request = new CustomerCountRequest();
        request.setCustomerName("John Doe");

        CustomerCountResponse response = (CustomerCountResponse) webServiceTemplate.marshalSendAndReceive(request);
        assertEquals(10, response.getCustomerCount());
    }

}