# Credit card preview
A simple composable component that displays credit/debit card information.

## Usage
```kotlin
@Composable
private fun CreditCardPreview() {
    CreditCard(
        bankName = "Banco agrícola",
        number = "00AA11BB22CC4310",
        expiration = "08/22",
        holderName = "carlos menjivar",
        cvc = "193"
    )
}
```

![credit card front empty](./images/credit_card_front_side.png)

```kotlin
@Preview(name = "Credit card back side")
@Composable
fun CreditCardBackPreview() {
    CreditCard(
        number = "00AA11BB22CC4310",
        holderName = "carlos menjivar",
        expiration = "08/02",
        bankName = "Bank name",
        flipped = true
    )
}
```
![credit card front empty](./images/credit_card_back_side.png)

## Next steps
- [x] Create an standar design for the card
- [x] Include back view
- [ ] Identify card entity
- [ ] Add inputs to fill the view
- [ ] Include testing
- [ ] Upload project to centralMaven