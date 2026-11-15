#!/usr/bin/env bash
set -euo pipefail

RUNDIR="${1:?usage: $0 <run-dir> <server-log>}"
STDOUT_LOG="${2:?usage: $0 <run-dir> <server-log>}"
MARKER="${MOD_STARTED_MARKER:?MOD_STARTED_MARKER is not set}"

FATAL=(
  'This crash report has been saved to'
  'Encountered an unexpected exception'
  'Exception caught during firing event'
  'NoClassDefFoundError'
  'for invalid side'
  'Fatal errors were detected'
  'LoaderExceptionModCrash'
  'The state engine was in incorrect state ERRORED and forced into state SERVER_STOPPED'
  'Exception stopping the server'
)

mapfile -t LOGS < <(find "$RUNDIR" -type f -path '*/logs/latest.log')
LOGS+=("$STDOUT_LOG")

fail() {
  echo "::error::$1"
  exit 1
}

mapfile -t crashes < <(find "$RUNDIR" -type f -path '*crash-reports/crash*.txt' | sort)
if [ "${#crashes[@]}" -gt 0 ]; then
  cat "${crashes[-1]}" >&2
  fail "Crash report detected: ${crashes[-1]}"
fi

for log in "${LOGS[@]}"; do
  [ -f "$log" ] || continue
  for pattern in "${FATAL[@]}"; do
    if grep -qF -- "$pattern" "$log"; then
      echo "::group::matches in $log"
      grep -nF -- "$pattern" "$log" | head -n 20
      echo "::endgroup::"
      fail "Found in $log: $pattern"
    fi
  done
done

[ -f "$MARKER" ] || fail "Startup marker not found: server did not reach FMLServerStartedEvent"

echo "Server started and stopped cleanly"