(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'clojurescript.core
   :output-to "out/clojurescript.js"
   :output-dir "out"})
