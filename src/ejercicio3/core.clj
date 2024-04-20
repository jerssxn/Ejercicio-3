(ns invoice-item)

(defn- discount-factor [{:invoice-item/keys [discount-rate]
                         :or                {discount-rate 0}}]
  (- 1 (/ discount-rate 100.0)))

(defn subtotal
  [{:invoice-item/keys [precise-quantity precise-price discount-rate]
    :as                item
    :or                {discount-rate 0}}]
  (* precise-price precise-quantity (discount-factor item)))



(ns test.invoice-item  ;Aquí estamos importando el módulo invoice-item que contiene la función subtotal, y también estamos importando el módulo de pruebas de Clojure
  (:require [clojure.test :refer :all]
            [invoice-item :as ii]))

(deftest test-subtotal-without_discount  ; Prueba que la función calcule correctamente el subtotal cuando no hay descuento
  (is (= 50 (ii/subtotal {:precise-quantity 5 :precise-price 10}))))

(deftest test-subtotal_with_discount  ; Prueba que la función calcule correctamente el subtotal cuando se aplica un descuento
  (is (= 40 (ii/subtotal {:precise-quantity 5 :precise-price 10 :discount-rate 20}))))

(deftest test-subtotal_with_zero_quantity   ; Prueba que el subtotal sea cero si la cantidad es cero
  (is (= 0 (ii/subtotal {:precise-quantity 0 :precise-price 10}))))

(deftest test-subtotal_with_zero_price  ;Prueba que el subtotal sea cero si el precio es cero
  (is (= 0 (ii/subtotal {:precise-quantity 5 :precise-price 0}))))

(deftest test-subtotal_with_negative_discount  ;Prueba que la función maneje correctamente un descuento negativo
  (is (= 60 (ii/subtotal {:precise-quantity 5 :precise-price 10 :discount-rate -20}))))
