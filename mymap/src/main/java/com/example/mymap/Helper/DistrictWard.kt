import DistrictWardID.Datum
import android.util.Log
import androidx.annotation.NonNull
import com.example.mymap.Helper.Extension
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistrictWard {
    fun getProvinceOffline() {
        val data =
            "[{\"ma\": \"46\",\"ten\": \"Tỉnh Thừa Thiên Huế\",\"cap\": \"tỉnh\"},{\"ma\": \"79\",\"ten\": \"Thành phố Hồ Chí Minh\",\"cap\": \"thành phố trung ương\"}]"
        val gson = GsonBuilder().create()!!
        val resource: List<Province> =
            gson.fromJson(
                data,
                Array<Province>::class.java
            ).toList()
        GlobalVariables.provinceID =
            arrayOfNulls<String>(resource.size)

        GlobalVariables.provinceName =
            arrayOfNulls<String>(resource.size)

        for ((i, item) in resource.withIndex()) {
            GlobalVariables.provinceID[i] = item.ma
            GlobalVariables.provinceName[i] = item.ten
        }
    }

    fun getProvince() {
        val search: Call<List<Province>> =
            ApiHelper().getInfoProvinceService()!!.getProvince()

        search.enqueue(object : Callback<List<Province>> {
            override fun onResponse(
                call: Call<List<Province>>,
                response: Response<List<Province>?>
            ) {

                if (response.code() == 200 && response.body()!!.isNotEmpty()) {
                    val resource: List<Province>? = response.body()

                    GlobalVariables.provinceID =
                        arrayOfNulls<String>(resource!!.size)

                    GlobalVariables.provinceName =
                        arrayOfNulls<String>(resource.size)

                    for ((i, item) in resource.withIndex()) {
                        GlobalVariables.provinceID[i] = item.ma
                        GlobalVariables.provinceName[i] = item.ten
                    }
                }
            }

            override fun onFailure(call: Call<List<Province>?>, t: Throwable) {
//                ToastUtils.showLong("Lỗi lấy thông tin Tỉnh/Thành, vui lòng thử lại sau!");
                Extension().showToast(
                    R.string.txt_error_get_province,
                    GlobalVariables.activity.applicationContext
                )
            }
        })
    }

    suspend fun getDistrictWardById(id: String): List<Province.Quanhuyen>? {
        var list: List<Province.Quanhuyen>? = null
        val search: Call<Province> =
            ApiHelper().getInfoProvinceService()!!.getDistrict(id)

        search.enqueue(object : Callback<Province> {
            override fun onResponse(
                call: Call<Province>,
                response: Response<Province?>
            ) {
                if (response.code() == 200) {
                    val resource: List<Province.Quanhuyen> = response.body()!!.quanhuyen
                    list = resource

//                    GlobalVariables.provinceID =
//                        arrayOfNulls<String>(resource!!.size)
//
//                    GlobalVariables.provinceName =
//                        arrayOfNulls<String>(resource!!.size)
//
//                    for ((i, item )in resource!!.withIndex()){
//                        GlobalVariables.provinceID[i] = item.ma
//                        GlobalVariables.provinceName[i] = item.ten
//                    }

                    GlobalVariables.wardName =
                        arrayOfNulls<Array<String>>(resource.size)
                    GlobalVariables.wardId =
                        arrayOfNulls<Array<String>>(resource.size)
                    GlobalVariables.districtName =
                        arrayOfNulls<String>(resource.size)
                    GlobalVariables.districtId =
                        arrayOfNulls<String>(resource.size)
                    for ((i, datum) in resource.withIndex()) {
                        GlobalVariables.districtName[i] = datum.ten
                        GlobalVariables.districtId[i] = datum.ma
                        val datumWards = datum.phuongxa
                        GlobalVariables.wardName[i] = arrayOfNulls<String>(
                            datumWards.size
                        )
                        GlobalVariables.wardId[i] =
                            arrayOfNulls<String>(
                                datumWards.size
                            )
                        for ((j, DatumWardList) in datumWards.withIndex()) {
                            GlobalVariables.wardName[i][j] = DatumWardList.ten
                            GlobalVariables.wardId[i][j] = DatumWardList.ma
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Province?>, t: Throwable) {
                Extension().showToast(
                    R.string.txt_error_get_province,
                    GlobalVariables.activity.applicationContext
                )

            }
        })

        Log.d("haha1", list?.size.toString())
        return list
    }


    fun getDistrictWardId() {
        val search: Call<DistrictWardID> =
            ApiHelper().getPlanningInfoService()!!.getDistrictWardId()

        search.enqueue(object : Callback<DistrictWardID?> {
            override fun onResponse(
                call: Call<DistrictWardID?>,
                response: Response<DistrictWardID?>
            ) {

                val resource: DistrictWardID? = response.body()
                val datumList: List<Datum> = resource?.data!!
                GlobalVariables.wardName =
                    arrayOfNulls<Array<String>>(datumList.size)
                GlobalVariables.wardId =
                    arrayOfNulls<Array<String>>(datumList.size)
                GlobalVariables.districtName =
                    arrayOfNulls<String>(datumList.size)
                GlobalVariables.districtId =
                    arrayOfNulls<String>(datumList.size)
                for ((i, datum) in datumList.withIndex()) {
                    GlobalVariables.districtName[i] = datum.tenquanhuyen
                    GlobalVariables.districtId[i] = datum.maquanhuyen
                    val datumWards = datum.phuongxa
                    GlobalVariables.wardName[i] = arrayOfNulls<String>(
                        datumWards!!.size
                    )
                    GlobalVariables.wardId[i] =
                        arrayOfNulls<String>(
                            datumWards.size
                        )
                    for ((j, DatumWardList) in datumWards.withIndex()) {
                        GlobalVariables.wardName[i][j] = DatumWardList.tenphuongxa
                        GlobalVariables.wardId[i][j] = DatumWardList.maphuongxa
                    }
                }
            }

            override fun onFailure(@NonNull call: Call<DistrictWardID?>, @NonNull t: Throwable) {
                Extension().showToast(
                    R.string.txt_error_data,
                    GlobalVariables.activity.applicationContext
                )

            }
        })
    }
}


