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
import timber.log.Timber


abstract class MviFragment<OUTPUT, INPUT> : ObservableSource<OUTPUT>, Consumer<INPUT>, Fragment() {

    private val source = PublishSubject.create<OUTPUT>()

    protected fun onNext(t: OUTPUT) {
        source.onNext(t)
    }

    override fun subscribe(observer: Observer<in OUTPUT>) {
        source.subscribe(observer)
    }

    abstract fun getLayoutResId(): Int
    abstract fun getBindings(): Bindings<OUTPUT, INPUT>
    abstract fun initView(view: View, savedInstanceState: Bundle?)
    open fun afterInitAction() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        getBindings().setup(this)
        afterInitAction()
    }
}

abstract class Bindings<OUTPUT, INPUT> {
    private var initiated: Boolean = false
    abstract fun create(view: MviFragment<OUTPUT, INPUT>)

    fun setup(view: MviFragment<OUTPUT, INPUT>) {
        if (!initiated) {
            create(view)
            initiated = true
        }
    }
}

abstract class Transformer<INPUT, OUTPUT> : (INPUT) -> OUTPUT? {

    private val tag by lazy { "TRANSFORMER ${this@Transformer.javaClass.simpleName}" }

    override fun invoke(input: INPUT): OUTPUT? {
        Timber.tag(tag).d("\nINPUT: \n$input")
        return action(input).also {
            Timber.tag(tag).d("\nOUTPUT: \n$it")
        }
    }

    abstract fun action(input: INPUT): OUTPUT?
}