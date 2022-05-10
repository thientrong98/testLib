import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetHelper : BottomSheetDialogFragment() {
    private var unbinder: Unbinder? = null
    private lateinit var btnCreatePost: Button
    private lateinit var btnCancelDownload: Button

    companion object {
        fun newInstance(): BottomSheetHelper {
            return BottomSheetHelper()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { setupBottomSheet(it) }
        return dialog
    }

    private fun setupBottomSheet(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheet = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet)
            ?: return
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        unbinder = ButterKnife.bind(this, view)
        btnCreatePost = view.findViewById(R.id.btnCreatePost)
        btnCancelDownload = view.findViewById(R.id.btn_cancel_download)

        btnCreatePost.setOnClickListener {
            clickCreatePost()
        }

        btnCancelDownload.setOnClickListener {
            dismiss()
        }

        return view
    }


    private fun clickCreatePost() {
        try {
            val callback = GlobalVariables.activity as DemoFragment.CreatePostListener
            callback.sendDataSuccess(GlobalVariables.planningInfo)
        } catch (e: Exception) {
            print(e.message)
        }
        dismiss()
    }
}