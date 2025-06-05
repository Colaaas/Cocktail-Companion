package fr.ensim.android.cocktailcompanion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.ensim.android.cocktailcompanion.R

val Inter = FontFamily(
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_thinitalic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.inter_extralight, FontWeight.ExtraLight),
    Font(R.font.inter_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_lightitalic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_mediumitalic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),

    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_bolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.inter_extrabold, FontWeight.ExtraBold),
    Font(R.font.inter_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),

    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_blackitalic, FontWeight.Black, FontStyle.Italic)
)

val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
