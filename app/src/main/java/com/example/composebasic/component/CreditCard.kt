package com.example.composebasic.component

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
import com.example.composebasic.R
import com.example.composebasic.model.CreditCardModel

@Composable
fun CreditCard(model: CreditCardModel, emptyChar: Char = 'x', backgroundColor: Color = Color.Blue) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(185.dp),
        shape = RoundedCornerShape(5.dp),
        backgroundColor = backgroundColor
    ) {
        ConstraintLayout {
            val (
                title, debit, chip,
                serialNumberBlockOne,
                serialNumberBlockTwo,
                serialNumberBlockThree,
                serialNumberBlockFour,
                goodThru,
                expiration, personName,
                ncNumber,
                visa
            ) = createRefs()

            val block = deserializeBlock(
                serial = model.number,
                emptyChar = emptyChar
            )

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "BancoAgricola"
            )

            Text(
                modifier = Modifier.constrainAs(debit) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
                fontSize = 11.sp,
                color = Color.White,
                text = "DÉBITO"
            )
            Image(
                painter = painterResource(id = R.drawable.chip_credit_card),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(chip) {
                        top.linkTo(title.bottom, margin = 14.dp)
                        start.linkTo(parent.start, margin = 30.dp)
                    }
                    .width(30.dp)
            )

            CreditCardBlockNumber(
                modifier = Modifier.constrainAs(serialNumberBlockOne) {
                    top.linkTo(chip.bottom, margin = 2.dp)
                    start.linkTo(chip.start)
                },
                block = block.first
            )


            CreditCardBlockNumber(
                modifier = Modifier.constrainAs(serialNumberBlockTwo) {
                    start.linkTo(serialNumberBlockOne.end, margin = 10.dp)
                    centerVerticallyTo(serialNumberBlockOne)
                },
                block = block.second
            )

            CreditCardBlockNumber(
                modifier = Modifier.constrainAs(serialNumberBlockThree) {
                    start.linkTo(serialNumberBlockTwo.end, margin = 10.dp)
                    centerVerticallyTo(serialNumberBlockTwo)
                },
                block = block.third
            )

            CreditCardBlockNumber(
                modifier = Modifier.constrainAs(serialNumberBlockFour) {
                    start.linkTo(serialNumberBlockThree.end, margin = 10.dp)
                    centerVerticallyTo(serialNumberBlockThree)
                },
                block = block.fourth
            )

            Text(
                modifier = Modifier.constrainAs(goodThru) {
                    end.linkTo(expiration.start, margin = 3.dp)
                    bottom.linkTo(expiration.bottom)
                },
                fontSize = 6.sp,
                color = Color.White,
                text = "GOOD\nTHRU"
            )

            Text(
                modifier = Modifier.constrainAs(expiration) {
                    top.linkTo(serialNumberBlockOne.bottom, margin = 10.dp)
                    centerHorizontallyTo(parent)
                },
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.White,
                text = model.expiration
            )
            Text(
                modifier = Modifier.constrainAs(personName) {
                    top.linkTo(expiration.bottom, margin = 2.dp)
                    start.linkTo(serialNumberBlockOne.start)
                },
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = Color.White,
                text = model.personName.uppercase()
            )
            Text(
                modifier = Modifier.constrainAs(ncNumber) {
                    top.linkTo(personName.bottom, margin = 0.dp)
                    start.linkTo(personName.start)
                },
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                color = Color.White,
                text = "N.C 003050309"
            )
            Text(
                modifier = Modifier.constrainAs(visa) {
                    end.linkTo(debit.end)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "VISA"
            )
        }
    }
}

@Composable
fun CreditCardBlockNumber(block: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        fontWeight = FontWeight.Light,
        fontFamily = FontFamily.Monospace,
        fontSize = 22.sp,
        color = Color.White,
        text = block
    )
}

class SerialBlock( val first: String, val second: String, val third: String, val fourth: String)

fun deserializeBlock(serial: String, emptyChar: Char = 'x') = SerialBlock(
    first = getSerialBlock(
        serial = serial,
        blockNumber = 1,
        emptyChar = emptyChar
    ),
    second = getSerialBlock(
        serial = serial,
        blockNumber = 2,
        emptyChar = emptyChar
    ),
    third = getSerialBlock(
        serial = serial,
        blockNumber =  3,
        emptyChar = emptyChar
    ),
    fourth = getSerialBlock(
        serial = serial,
        blockNumber = 4,
        emptyChar = emptyChar
    )
)

fun getSerialBlock(serial: String, blockNumber: Int, emptyChar: Char = '0'): String {
    val length = serial.length
    var block = "".padEnd(4, emptyChar)
    val start = (blockNumber - 1) * 4
    val end = blockNumber * 4

    if(length in start until end) {
        block = serial
            .substring(start, length)
            .padEnd(4, emptyChar)
    } else if(serial.length >= start) {
        block = serial.substring(start, end)
    }
    return block
}

@Preview
@Composable
fun CreditCardPreview() {
    val creditCard = CreditCardModel(
        number = "00AA11BB22CC4",
        personName = "carlos menjivar",
        expiration = "08/22"
    )
    CreditCard(model = creditCard)
}