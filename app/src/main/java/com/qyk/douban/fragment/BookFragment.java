package com.qyk.douban.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qyk.douban.R;
import com.qyk.douban.adapter.BookAdapter;
import com.qyk.douban.model.Book;

import java.util.List;

public class BookFragment extends Fragment{

	private List<Book> mbooks;
	private String curFlag;
	private RecyclerView recyclerView;
	private BookAdapter bookAdapter;
	private SwipeRefreshLayout refreshLayout;

	public static BookFragment newInstance(String flag){
		BookFragment fragment = new BookFragment();
        Bundle bundle = new Bundle();  
        bundle.putString("Flag", flag);
        fragment.setArguments(bundle);  
		return fragment;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		Bundle args = getArguments();
		if (args != null) {  
			 curFlag = args.getString("Flag");
		}

	} 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_book, container, false);
		initViews(view);
		setActions();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		doSearchBookByName("完美");
	}

	private void initViews(View view){
		refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_book);
		recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_book);
	}

	private void setActions(){
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				doSearchBookByName("完美");
			}
		});
		recyclerView.setVisibility(View.GONE);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//			}
//
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				boolean enable = false;
//				if(recyclerView != null && recyclerView.getChildCount() > 0){
//					// check if the first item of the list is visible
////					boolean firstItemVisible = recyclerView.getFirstVisiblePosition() == 0;
////					// check if the top of the first item is visible
//					boolean topOfFirstItemVisible = recyclerView.getChildAt(0).getTop() == 0;
//					// enabling or disabling the refresh layout
//					enable =  topOfFirstItemVisible;
//				}
//				refreshLayout.setEnabled(enable);
//			}
//		});
	}

	public void doSearchBookByName(String name){
		Book.searchBooks(name, 0, 50, new Book.IBookResponse<List<Book>>() {
			@Override
			public void onData(List<Book> books) {
				for (Book book:books){
					System.out.println(book.toString());
				}
				mbooks=books;
				bookAdapter=new BookAdapter(books,getActivity());
				recyclerView.setAdapter(bookAdapter);
				recyclerView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFailed(List<Book> data) {
				Toast.makeText(getActivity(), "联网失败", Toast.LENGTH_SHORT).show();
			}
		});
		refreshLayout.setRefreshing(false);
	}
}