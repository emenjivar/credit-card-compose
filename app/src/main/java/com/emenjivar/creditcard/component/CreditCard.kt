package com.emenjivar.creditcard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.emenjivar.creditcard.R
import com.emenjivar.creditcard.model.CreditCardModel
import com.emenjivar.creditcard.utils.CardNumberParser

@Composable
fun CreditCard(
    model: CreditCardModel,
    emptyChar: Char = 'x',
    backgroundColor: Color = Color.Blue
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(185.dp),
        shape = RoundedCornerShape(5.dp),
        backgroundColor = backgroundColor
    ) {
        ConstraintLayout {
            val (
                title,
                iChip,
                lNumberBlockOne,
                lNumberBlockTwo,
                lNumberBlockThree,
                lNumberBlockFour,
                lGoodThru,
                lExpiration,
                lHolderName,
                iCardEntity
            ) = createRefs()

            val cardNumber = CardNumberParser(
                number = model.number,
                emptyChar = emptyChar
            )

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                },
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = model.bankName
            )

            Image(
                painter = painterResource(id = R.drawable.chip_credit_card),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(iChip) {
                        top.linkTo(title.bottom, margin = 10.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
                    .width(30.dp)
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockOne) {
                    top.linkTo(iChip.bottom, margin = 2.dp)
                    start.linkTo(iChip.start)
                },
                block = cardNumber.first
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockTwo) {
                    start.linkTo(lNumberBlockOne.end, margin = 10.dp)
                    centerVerticallyTo(lNumberBlockOne)
                },
                block = cardNumber.second
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockThree) {
                    start.linkTo(lNumberBlockTwo.end, margin = 10.dp)
                    centerVerticallyTo(lNumberBlockTwo)
                },
                block = cardNumber.third
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockFour) {
                    start.linkTo(lNumberBlockThree.end, margin = 10.dp)
                    centerVerticallyTo(lNumberBlockThree)
                },
                block = cardNumber.fourth
            )

            Text(
                modifier = Modifier.constrainAs(lGoodThru) {
                    end.linkTo(lExpiration.start, margin = 3.dp)
                    bottom.linkTo(lExpiration.bottom)
                },
                fontSize = 6.sp,
                color = Color.White,
                text = "GOOD\nTHRU"
            )

            Text(
                modifier = Modifier.constrainAs(lExpiration) {
                    top.linkTo(lNumberBlockOne.bottom, margin = 5.dp)
                    centerHorizontallyTo(parent)
                },
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.White,
                text = model.expiration
            )
            Text(
                modifier = Modifier.constrainAs(lHolderName) {
                    top.linkTo(lExpiration.bottom, margin = 5.dp)
                    start.linkTo(lNumberBlockOne.start)
                },
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.White,
                text = model.holderName.uppercase()
            )

            Image(
                modifier = Modifier
                    .constrainAs(iCardEntity) {
                        end.linkTo(parent.end, margin = 8.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    }
                    .height(40.dp),
                painter = painterResource(id = R.drawable.logo_mastercard),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun CardNumberBlock(block: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        fontWeight = FontWeight.Light,
        fontFamily = FontFamily.Monospace,
        fontSize = 22.sp,
        color = Color.White,
        text = block
    )
}

@Preview
@Composable
fun CreditCardPreview() {
    val creditCard = CreditCardModel(
        number = "00AA11BB22CC4",
        holderName = "carlos menjivar",
        expiration = "08/22"
    )
    CreditCard(model = creditCard)
}