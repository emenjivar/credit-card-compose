# Credit card preview
A simple composable component that displays a **credit card preview** and provide a **form** to enter de information, including field validations.

This component supports (*at the moment*) visa and mastercard on the preview.

## How to use?
Initialize the viewModel provided by the project

```kotlin
class Activity : ComponentActivity() {
    // Initialize here
    private val viewModel by viewModels<CreditCardViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // This function is declarated in the next step
            LayoutCreditCard(
                viewModel = viewModel
            )
        }
    }
}
```

Then create the composable function, using the viewmodel as a parameter
```kotlin
@Composable
fun LayoutCreditCard(viewModel: CreditCardViewModel) {
    Column {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp, 
                    top = 16.dp, 
                    end = 16.dp
                )
        ) {
            CreditCard(
                bankName = "BancoAgrícola",
                number = viewModel.number,
                expiration = viewModel.expiration,
                holderName = viewModel.name,
                cvc = viewModel.cvc,
                flipped = viewModel.flipped,
                emptyChar = 'X'
            )

            Spacer(modifier = Modifier.size(16.dp))

            CreditCardInputs(viewModel = viewModel)
        }
    }
}

```

![credit card front empty](./images/card_input.gif)

The **credit card preview** and **form** are separated components, so you can use them independently.

## Contribute
I will be more than happy to receive your PR, I am open to suggestions or modifications.

As for commits, I use [gitmoji](https://gitmoji.dev/) to keep the history small and consistent. 

## Next steps
- [x] Create an standar design for the card
- [x] Include back view
- [X] Identify card entity
- [X] Add inputs to fill the view
- [ ] Include testing
- [ ] Upload project to centralMaven