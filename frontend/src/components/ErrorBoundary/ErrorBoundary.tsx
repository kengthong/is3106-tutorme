import React, { Component } from "react";

type Props = {
  render: RenderPropsFunction;
  // error boundary needs to be used around children to be meaningful
  children: React.ReactNode;
};

type RenderPropsFunction = (
  error: any,
  errorInfo: any,
  onReset: () => void
) => React.ReactNode;

type State = {
  error: any;
  errorInfo: any;
};

const DEFAULT_RENDER: RenderPropsFunction = (error, errorInfo, onReset) => (
  <div>
    <h4>Something went wrong.</h4>
    <details>
      <summary>Details</summary>
      <p>{error.toString()}</p>
      <pre>
        Error details <br />
        {errorInfo.componentStack}
      </pre>
    </details>
    <button type="button" onClick={onReset}>
      Try Again
    </button>
  </div>
);

class ErrorBoundary extends Component<Props, State> {
  state = { error: null, errorInfo: null };

  // eslint-disable-next-line react/static-property-placement
  static defaultProps = {
    render: DEFAULT_RENDER,
  };

  componentDidCatch(error: Error, errorInfo: any) {
    this.setState({ error, errorInfo });
    console.log("error =", error);
  }

  reset = () => {
    this.setState({ error: null, errorInfo: null });
  };

  render() {
    const { render, children } = this.props;
    const { error, errorInfo } = this.state;

    if (error) {
      return render(error, errorInfo, this.reset);
    }
    return children;
  }
}

export default ErrorBoundary;
