(ns yellow-hammock.handler
  (:require [compojure.core :refer :all ]
            [compojure.route :refer [not-found resources]]
            [hiccup.page :refer [include-js include-css html5]]
            [yellow-hammock.middleware :refer [wrap-middleware]]
            [config.core :refer [env]]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            ))

(def mount-target
  [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]])

(defn head []
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   (include-css (if (env :dev) "/css/site.css" "/css/site.min.css"))])

(defn loading-page []
  (html5
    (head)
    [:body {:class "body-container"}
     mount-target
     (include-js "/js/app.js")]))

(def fb-messages "https://graph.facebook.com/v2.6/me/messages")
(def fb-convo "https://graph.facebook.com/v2.6/me/conversations")
(def qs "EAACN3fv7nN0BAO1qGPq12NswFtP8L9RWN3YE9IFl1K9wvAQDla4THwqg7Bpwh73ebM8MSvnfJMfZAvPZBTrWroalp7ZACZBcw5DSS1xFU2GDqHIMKpb1K9wkoMMfmfFFntzNfmrwuA33Vu45UQq2vuoqyzKsWSINfrFsksbtfAZDZD")

(defn get-convo [] (client/get  (str fb-convo "?access_token=" qs) {
                                                                    :throw-exceptions false}))

((get-convo) :body)




(defroutes my_routes
  (GET "/" [] (loading-page))
  (GET "/fb/webhook"  request (println str request))
  (POST  "/fb/webhook" request (str request ))

  (resources "/")
  (not-found "Not Found"))

(def app (wrap-middleware #'my_routes))







