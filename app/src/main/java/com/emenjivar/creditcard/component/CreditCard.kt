package com.emenjivar.creditcard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.emenjivar.creditcard.R
import com.emenjivar.creditcard.model.CreditCardModel
import com.emenjivar.creditcard.utils.CardNumberParser

@Composable
fun CreditCardContainer(
    backgroundColor: Color = Color.Blue,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.credit_card_width))
            .height(dimensionResource(id = R.dimen.credit_card_height)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.credit_card_round_corner)),
        backgroundColor = backgroundColor
    ) {
        content()
    }
}

@Composable
fun CreditCard(
    model: CreditCardModel,
    emptyChar: Char = 'x',
    backgroundColor: Color = Color.Blue,
    flipped: Boolean = false
) {
    if(flipped) {
        CreditCardBackSide(
            model = model,
            emptyChar = emptyChar,
            backgroundColor = backgroundColor
        )
    } else {
        CreditCardFrontSide(
            model = model,
            emptyChar = emptyChar,
            backgroundColor = backgroundColor
        )
    }
}

@Composable
fun CreditCardFrontSide(
    model: CreditCardModel,
    emptyChar: Char = 'x',
    backgroundColor: Color = Color.Blue
) {
    CreditCardContainer(
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

            val cardPadding = dimensionResource(R.dimen.credit_card_padding)
            val spaceCardNumberBlock = dimensionResource(R.dimen.credit_card_space_number_block)

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = cardPadding)
                    end.linkTo(parent.end, margin = cardPadding)
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
                        start.linkTo(parent.start, margin = cardPadding)
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
                    start.linkTo(lNumberBlockOne.end, margin = spaceCardNumberBlock)
                    centerVerticallyTo(lNumberBlockOne)
                },
                block = cardNumber.second
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockThree) {
                    start.linkTo(lNumberBlockTwo.end, margin = spaceCardNumberBlock)
                    centerVerticallyTo(lNumberBlockTwo)
                },
                block = cardNumber.third
            )

            CardNumberBlock(
                modifier = Modifier.constrainAs(lNumberBlockFour) {
                    start.linkTo(lNumberBlockThree.end, margin = spaceCardNumberBlock)
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
fun CreditCardBackSide(
    model: CreditCardModel,
    emptyChar: Char = 'x',
    backgroundColor: Color = Color.Blue
) {
    CreditCardContainer(
        backgroundColor = backgroundColor
    ) {
        ConstraintLayout {
            val (magneticStrip, signature, cvc, bankName) = createRefs()
            val cardPadding = dimensionResource(id = R.dimen.credit_card_padding)
            val cardNumber = CardNumberParser(
                number = model.number,
                emptyChar = emptyChar
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.credit_card_magnetic_band_height))
                    .background(Color.Black)
                    .constrainAs(magneticStrip) {
                        top.linkTo(parent.top, margin = cardPadding)
                    }
            )

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(30.dp)
                    .background(Color.White)
                    .constrainAs(signature) {
                        top.linkTo(magneticStrip.bottom, margin = cardPadding)
                        start.linkTo(parent.start, margin = cardPadding)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End,
                    text = cardNumber.fourth
                )
            }

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(30.dp)
                    .background(Color.White)
                    .constrainAs(cvc) {
                        top.linkTo(signature.top)
                        start.linkTo(signature.end, margin = cardPadding)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = model.cvc
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(bankName) {
                        end.linkTo(parent.end, margin = cardPadding)
                        bottom.linkTo(parent.bottom, margin = cardPadding)
                    },
                color = Color.White,
                fontSize = 12.sp,
                text = model.bankName
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

@Preview(name = "Credit card front side")
@Composable
fun CreditCardPreview() {
    val creditCard = CreditCardModel(
        number = "00AA11BB22CC4310",
        holderName = "carlos menjivar",
        expiration = "08/22"
    )
    CreditCard(
        model = creditCard
    )
}

@Preview(name = "Credit card back side")
@Composable
fun ReverseCreditCardPreview() {
    val creditCard = CreditCardModel(
        number = "00AA11BB22CC4310",
        holderName = "carlos menjivar",
        expiration = "08/22",
        cvc = "193",
        bankName = "BancoAgrícola"
    )
    CreditCard(
        model = creditCard,
        flipped = true
    )
}