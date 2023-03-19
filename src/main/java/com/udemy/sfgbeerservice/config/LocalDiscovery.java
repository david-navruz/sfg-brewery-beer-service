package com.udemy.sfgbeerservice.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@EnableDiscoveryClient
@Configuration
public class LocalDiscovery {


}

/*
Spring Cloud - @EnableDiscoveryClient
What is Service Discovery in Microservices?
Microservices service discovery is a way for applications and microservices to locate each other on a network.
Service discovery implementations within microservices architecture discovery includes both:
• a central server (or servers) that maintain a global view of addresses.
• clients that connect to the central server to update and retrieve addresses.

What are the Advantages of Service Discovery (Server-side & Client-side)?
The advantage of Server-side service discovery is that it makes the client application lighter
as it does not have to deal with the lookup procedure and makes a request for services to the router.
The advantage of Client-side service discovery is that the client application does not have to traffic
through a router or a load balancer and therefore can avoid that extra hop.

 */