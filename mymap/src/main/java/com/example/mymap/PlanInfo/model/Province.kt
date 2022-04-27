import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Province :Serializable{
    @SerializedName("ma"  ) var ma  : String? = null
    @SerializedName("ten" ) var ten : String? = null
    @SerializedName("cap" ) var cap : String? = null
    @SerializedName("quanhuyen" ) var quanhuyen : ArrayList<Quanhuyen> = arrayListOf()


    data class Quanhuyen (

        @SerializedName("ma"       ) var ma       : String?             = null,
        @SerializedName("cap"      ) var cap      : String?             = null,
        @SerializedName("ten"      ) var ten      : String?             = null,
        @SerializedName("phuongxa" ) var phuongxa : ArrayList<Phuongxa> = arrayListOf()
    )

    data class Phuongxa (
        @SerializedName("ma"  ) var ma  : String? = null,
        @SerializedName("cap" ) var cap : String? = null,
        @SerializedName("ten" ) var ten : String? = null

    )

}