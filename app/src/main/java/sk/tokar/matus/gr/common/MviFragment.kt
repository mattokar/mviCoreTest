package sk.tokar.matus.gr.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject


abstract class MviFragment<OUTPUT, INPUT> : ObservableSource<OUTPUT>, Consumer<INPUT>, Fragment() {

    private val source = PublishSubject.create<OUTPUT>()

    protected fun onNext(t: OUTPUT) {
        source.onNext(t)
    }

    override fun subscribe(observer: Observer<in OUTPUT>) {
        source.subscribe(observer)
    }

    abstract fun getLayoutResId(): Int

    abstract fun init(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }
}