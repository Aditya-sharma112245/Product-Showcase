import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class products(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String?,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int?,
    val brand: String?
) : Parcelable
