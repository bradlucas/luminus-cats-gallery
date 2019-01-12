# luminus-cats-gallery

A sample application generated with Luminus which demonstrates it's
support for building RESTful web services and displaying the
results using ClojureScript.

Why?

Chapter Six of `Web Development with Clojure` has a sample application
which demonstrates how to use Compojure-API to build a RESTful
service. The example includes the ClojureScript profile to demonstrate
calling an API.

I thought it useful to rebuild the example without the ClojureScript
support to keep the Compojure-API example portion separate and more
easy to understand. This step is available at the following repo.

[https://github.com/bradlucas/luminus-cats](https://github.com/bradlucas/luminus-cats)

Following that excercise I decided to add the ClojureScript portion
back in. The result is this project.

I suggest if you find this project that you read Chapter Six of `Web
Development with Clojure` first.

## Setup

- Copy dev-config.edn.example to dev-config.edn
- Request an API key from the Cats API site. [https://thecatapi.com/](https://thecatapi.com/)
- Add your API key value to :api-key in dev-config.edn

## Running

To start run

    lein run 

## License

Copyright © 2019 Brad Lucas

