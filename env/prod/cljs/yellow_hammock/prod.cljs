(ns yellow-hammock.prod
  (:require [yellow-hammock.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
