# Custom protocol

It's possible to implement your custom protocol, e.g. `lin:your-protocol`.

You need to:

- create a package which would be the name of your protocol, e.g. `lin`
- create a [`LinConnection`](./src/main/java/lin/louis/custom_protocol/lin/LinConnection.java) that implemets `URLConnection`
- add a [`Handler`](./src/main/java/lin/louis/custom_protocol/lin/Handler.java) class that create a new `LinConnection`
  - :warning: the class MUST be exactly named `Handler`

That's all!
