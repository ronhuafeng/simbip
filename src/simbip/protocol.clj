(ns simbip.protocol)

(defprotocol Accessible
  (retrieve-port
    [this]
    [this port])
  (assign-port!
    [this port token])
  (add-token!
    [this token])
  (clear!
    [this])
  (enable!
    [this])
  (top-priority
    [this rules selections])
  (get-time
    [this]
    [this port])
  (set-time
    [this new-value]
    [this port new-value])

  (get-variable
    [this attr]
    [this port attr] ;; or this index attr
    )
  (set-variable
    [this attr new-value]
    [this port attr new-value]))



(defprotocol Fireable
  (fire! [this]))


(defprotocol Queryable
  (enable?
    [this] [this port] [this place port])
  (equal-name?
    [this that])
  (export?
    [this])
  (contain-port?
    [this port]))
