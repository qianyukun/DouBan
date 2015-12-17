package com.qyk.douban.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
	private SearchView searchView;
	private ProgressBar progressBar;
	private String searchKey="";

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
//		doSearchBookByName("英语");
	}

	private void initViews(View view){
		refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_book);
		recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview_book);
		searchView= (SearchView) view.findViewById(R.id.search_book);
		progressBar= (ProgressBar) view.findViewById(R.id.progressbar_book);
	}

	private void setActions(){
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				doSearchBookByName(searchKey);
			}
		});
		recyclerView.setVisibility(View.GONE);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setItemAnimator(new DefaultItemAnimator());

		searchView.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				return true;
			}
		});
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				doSearchBookByName(query);
				searchKey=searchView.getQuery().toString();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
	}

	public void doSearchBookByName(String name){
		progressBar.setVisibility(View.VISIBLE);
		Book.searchBooks(name, 0, 100, new Book.IBookResponse<List<Book>>() {
			@Override
			public void onData(List<Book> books) {
				if(books==null||books.size()<=0)
				{
					Toast.makeText(getActivity(), "未查找到相关图书", Toast.LENGTH_SHORT).show();
				}
				for (Book book:books){
					System.out.println(book.toString());
				}
				mbooks=books;
				bookAdapter=new BookAdapter(books,getActivity());
				recyclerView.setAdapter(bookAdapter);
				recyclerView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onFailed(List<Book> data) {
				Toast.makeText(getActivity(), "联网失败", Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}
		});
		refreshLayout.setRefreshing(false);
	}
}