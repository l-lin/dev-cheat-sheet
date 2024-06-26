# Logging with AOP

Nowadays, there are lots of tool to observe your application (e.g. OpenTelementry),
and I think if you can use such tool / approach, you should definitely use it.

However, if you are stuck with logs, and you want to automatically add [MDC](https://www.slf4j.org/manual.html#mdc),
and some logs / metrics on your methods, you can use [AOP](https://en.wikipedia.org/wiki/Aspect-oriented_programming)
to do it automatically.

This project is an implementation example of such approach.

## Concept

The idea is to use a [`@Observable`](./src/main/java/lin/louis/logging_with_aop/logging/Observable.java)
annotation on each method we want to observe.

The [`Observer`](./src/main/java/lin/louis/logging_with_aop/logging/Observer.java)
is the "glue" that is used to perform the actual work, i.e. add the MDC, logs, actions
to perform on success and failure.

Finally the [`ObserverAspect`](./src/main/java/lin/louis/logging_with_aop/logging/aop/ObserverAspect.java)
is the implementation of the AOP that is triggered each time the method annotated
by `@Observable` is called, and will execute the `Observer` with the right parameters.

## Usage

```bash
$ # Perform a pet purchase.
$ jo petName=foo ownerName=popo | http :8080/pets
HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Wed, 26 Jun 2024 10:58:56 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "id": "48f784f2-b3bf-446a-a5fa-ef7829d41cc3",
    "name": "foo"
}

$ # Simulating finding a non-existing pet.
$ http :8080/pets/non-existing
HTTP/1.1 404
Connection: keep-alive
Content-Length: 0
Date: Wed, 26 Jun 2024 10:59:16 GMT
Keep-Alive: timeout=60

$ # Find an existing pet.
$ http :8080/pets/48f784f2-b3bf-446a-a5fa-ef7829d41cc3
HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Wed, 26 Jun 2024 10:59:32 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "id": "48f784f2-b3bf-446a-a5fa-ef7829d41cc3",
    "name": "foo"
}

$ # Simulate a failed purchase.
$ jo petName=fail ownerName=popo | http :8080/pets
HTTP/1.1 500
Connection: close
Content-Type: application/json
Date: Wed, 26 Jun 2024 11:00:18 GMT
Transfer-Encoding: chunked

{
    "error": "Internal Server Error",
    "path": "/pets",
    "status": 500,
    "timestamp": "2024-06-26T11:00:18.764+00:00"
}

$ # Get the prometheus metrics.
$ # We can see the counter has incremented.
$ http :9121/actuator/prometheus | rg purchase
# HELP purchase_total number of paired camera
# TYPE purchase_total counter
purchase_total{status="ko"} 1.0
purchase_total{status="ok"} 1.0
```

And in the application logs, we can see the MDC that are added:

```log
12:58:56.106 [http-nio-8080-exec-1] pet_name=foo, owner_name=popo l.l.l.pet.PurchasePet [INFO] - START PurchasePet
12:58:56.109 [http-nio-8080-exec-1] pet_name=foo, owner_name=popo l.l.l.pet.PurchasePet [INFO] - SUCCESS PurchasePet
12:59:16.557 [http-nio-8080-exec-3] pet_id=non-existing l.l.l.pet.FindPet [INFO] - START FindPet
12:59:16.558 [http-nio-8080-exec-3] pet_id=non-existing l.l.l.pet.FindPet [ERROR] - FAILURE FindPet
12:59:32.628 [http-nio-8080-exec-5] pet_id=48f784f2-b3bf-446a-a5fa-ef7829d41cc3 l.l.l.pet.FindPet [INFO] - START FindPet
12:59:32.629 [http-nio-8080-exec-5] pet_id=48f784f2-b3bf-446a-a5fa-ef7829d41cc3 l.l.l.pet.FindPet [INFO] - SUCCESS FindPet

13:00:18.753 [http-nio-8080-exec-7] pet_name=fail, owner_name=popo l.l.l.pet.PurchasePet [INFO] - START PurchasePet
13:00:18.753 [http-nio-8080-exec-7] pet_name=fail, owner_name=popo l.l.l.pet.PurchasePet [ERROR] - FAILURE PurchasePet
13:00:18.756 [http-nio-8080-exec-7]  o.a.c.c.C.[.[.[.[dispatcherServlet] [ERROR] - Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.IllegalStateException: simulating failure] with root cause
java.lang.IllegalStateException: simulating failure
        at lin.louis.logging_with_aop.pet.PetRepository$Simulation.save(PetRepository.java:51)
```
