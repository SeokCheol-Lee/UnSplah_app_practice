package com.example.unsplash_app_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.unsplash_app_practice.databinding.ActivityMainBinding
import com.example.unsplash_app_practice.retrofit.RetrofitManager
import com.example.unsplash_app_practice.utils.Constants
import com.example.unsplash_app_practice.utils.Constants.TAG
import com.example.unsplash_app_practice.utils.RESPONSE_STATE
import com.example.unsplash_app_practice.utils.SEARCH_TYPE
import com.example.unsplash_app_practice.utils.onMyTextChanged

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

       findViewById<ProgressBar>(R.id.btn_progress).bringToFront()


       Log.d(TAG, "MainActivity - onCreate()")

       //라디오 그룹 가져옴
       mainBinding.searchTermRadioGroup.setOnCheckedChangeListener { _, checkedId ->
           //switch문
           when(checkedId){
               R.id.photo_search_radio_btn -> {
                   Log.d(TAG,"사진검색 버튼 클릭")
                   mainBinding.searchTermTextLayout.hint = "사진검색"
                   mainBinding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_photo_library,resources.newTheme())
                   this.currentSearchType = SEARCH_TYPE.PHOTO
               }
               R.id.user_search_radio_btn ->{
                   Log.d(TAG,"사용자검색 버튼 클릭")
                   mainBinding.searchTermTextLayout.hint = "사용자검색"
                   mainBinding.searchTermTextLayout.startIconDrawable = resources.getDrawable(R.drawable.ic_person,resources.newTheme())
                   this.currentSearchType = SEARCH_TYPE.USER
               }
           }
           Log.d(TAG,"Mainactiivty - onCheckedChange : $currentSearchType")
       }
       //텍스트가 변경이 되었을때
        mainBinding.serachTermEditText.onMyTextChanged {
            if(it.toString().count() > 0){
                findViewById<FrameLayout>(R.id.frame_search_btn).visibility = View.VISIBLE
                mainBinding.searchTermTextLayout.helperText=" "

                //스크롤뷰를 올린다
                mainBinding.mainScrollview.scrollTo(0,200)
            }else{
                findViewById<FrameLayout>(R.id.frame_search_btn).visibility = View.INVISIBLE
                mainBinding.searchTermTextLayout.helperText="검색어를 입력해주세요"
            }
            if(it.toString().count() == 12){
                Log.d(TAG,"에러띄우기")
                Toast.makeText(this,"검색어는 12자 까지만 입력 가능",Toast.LENGTH_SHORT).show()
            }
        }
       findViewById<Button>(R.id.btn_search).setOnClickListener {
           this.handleSearchButtonUi()
           val userSearchInput = mainBinding.serachTermEditText.text.toString()

           Log.d(TAG,"검색 버튼 클릭 $currentSearchType")
           RetrofitManager.instance.searchPhotos(searchTerm = userSearchInput, completion = {
               responseState, responseDataArrayList ->
               when(responseState){
                   RESPONSE_STATE.OKAY ->{
                       Log.d(TAG,"api 호출 성공 : ${responseDataArrayList?.size}")
                       val intent: Intent = Intent(this,PhotoCollectionActivity::class.java)
                       val bundle = Bundle()
                       bundle.putSerializable("photo_array_list",responseDataArrayList)
                       intent.putExtra("array_bundle",bundle)
                       intent.putExtra("search_term",userSearchInput)
                       startActivity(intent)
                   }
                   RESPONSE_STATE.FAIL -> {
                       Log.d(TAG,"api 호출 실패 : $responseDataArrayList")
                       Toast.makeText(this,"api 호출 에러",Toast.LENGTH_SHORT).show()
                   }

                   RESPONSE_STATE.NO_CONTENT -> {
                       Toast.makeText(this,"검색결과가 없음",Toast.LENGTH_SHORT).show()
                   }
               }
               findViewById<ProgressBar>(R.id.btn_progress).visibility = View.INVISIBLE
               findViewById<Button>(R.id.btn_search).text="검색"
               mainBinding.serachTermEditText.setText("")
           })


       }
    }//onCreate
    private fun handleSearchButtonUi(){
        findViewById<ProgressBar>(R.id.btn_progress).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_search).text=""
//        Handler().postDelayed({
//            findViewById<ProgressBar>(R.id.btn_progress).visibility = View.INVISIBLE
//            findViewById<Button>(R.id.btn_search).text="검색"
//        },1500)
    }
}