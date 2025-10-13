package com.nutrisportclone.shared

import nutrisportclone.shared.generated.resources.Res
import nutrisportclone.shared.generated.resources.back_arrow
import nutrisportclone.shared.generated.resources.book
import nutrisportclone.shared.generated.resources.cat
import nutrisportclone.shared.generated.resources.check
import nutrisportclone.shared.generated.resources.checkmark_image
import nutrisportclone.shared.generated.resources.close
import nutrisportclone.shared.generated.resources.delete
import nutrisportclone.shared.generated.resources.dollar
import nutrisportclone.shared.generated.resources.edit
import nutrisportclone.shared.generated.resources.google_logo
import nutrisportclone.shared.generated.resources.grid
import nutrisportclone.shared.generated.resources.home
import nutrisportclone.shared.generated.resources.india
import nutrisportclone.shared.generated.resources.log_in
import nutrisportclone.shared.generated.resources.log_out
import nutrisportclone.shared.generated.resources.map_pin
import nutrisportclone.shared.generated.resources.menu
import nutrisportclone.shared.generated.resources.minus
import nutrisportclone.shared.generated.resources.paypal_logo
import nutrisportclone.shared.generated.resources.plus
import nutrisportclone.shared.generated.resources.right_arrow
import nutrisportclone.shared.generated.resources.search
import nutrisportclone.shared.generated.resources.serbia
import nutrisportclone.shared.generated.resources.shopping_cart
import nutrisportclone.shared.generated.resources.shopping_cart_image
import nutrisportclone.shared.generated.resources.unlock
import nutrisportclone.shared.generated.resources.usa
import nutrisportclone.shared.generated.resources.user
import nutrisportclone.shared.generated.resources.vertical_menu
import nutrisportclone.shared.generated.resources.warning
import nutrisportclone.shared.generated.resources.weight

/**
 * @Res.drawable is only available in shared module
 * @Resources object is created to use them through all modules
 */

object Resources {
    object Icon {
        val Plus = Res.drawable.plus
        val Minus = Res.drawable.minus
        val SignIn = Res.drawable.log_in
        val SignOut = Res.drawable.log_out
        val Unlock = Res.drawable.unlock
        val Search = Res.drawable.search
        val Person = Res.drawable.user
        val Checkmark = Res.drawable.check
        val Edit = Res.drawable.edit
        val Menu = Res.drawable.menu
        val BackArrow = Res.drawable.back_arrow
        val RightArrow = Res.drawable.right_arrow
        val Home = Res.drawable.home
        val ShoppingCart = Res.drawable.shopping_cart
        val Categories = Res.drawable.grid
        val Dollar = Res.drawable.dollar
        val MapPin = Res.drawable.map_pin
        val Close = Res.drawable.close
        val Book = Res.drawable.book
        val VerticalMenu = Res.drawable.vertical_menu
        val Delete = Res.drawable.delete
        val Warning = Res.drawable.warning
        val Weight = Res.drawable.weight
    }
    object Image {
        val ShoppingCart = Res.drawable.shopping_cart_image
        val Checkmark = Res.drawable.checkmark_image
        val Cat = Res.drawable.cat
        val GoogleLogo = Res.drawable.google_logo
        val PaypalLogo = Res.drawable.paypal_logo
    }
    object Flag {
        val India = Res.drawable.india
        val Usa = Res.drawable.usa
        val Serbia = Res.drawable.serbia
    }
}