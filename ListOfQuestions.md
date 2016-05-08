# Questions

* Why clojure.set/intersection

* (facts "vectors"
        [(:a :b :c
          [:a :b :c]
          [:a :b :c]  => (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))])

* wrap block with parentsesis

(defn	alice-is	[input]
	->>	(loop	[in	input  out	[]] <--
				(if	(empty?	in)
						out
						(recur	(rest	in)
						(conj out (str	"Alice	is	"	(first	in)))))))