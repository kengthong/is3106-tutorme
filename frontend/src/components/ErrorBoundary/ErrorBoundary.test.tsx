import React from "react";
import { render } from "@testing-library/react";
import ErrorBoundary from "./ErrorBoundary";

test("renders without crashing", () => {
  render(
    <ErrorBoundary>
      <p>component</p>
    </ErrorBoundary>
  );
});

// are we testing if react is working ???
test.todo("renders children if provided");
test.todo("renders nothing if no children provided");

test.todo("catches error and displays default fallback UI");
test.todo("catches error and displays passed in fallback UI");
