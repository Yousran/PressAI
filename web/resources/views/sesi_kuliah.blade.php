@extends('layouts.layout_table')

@section('styles')
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.10.0/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="{{ asset('/vendor/timepicker.css') }}">
@endsection

@section('columns')    


<div class="card m-3">
    <div class="card-body">
        <h5 class="card-title">{{ $sesi_kuliahs['mata_kuliah_name']}}</h5>
        <h6 class="card-title">{{ $sesi_kuliahs['mata_kuliah_code']}}</h6>
        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#tambah_role">Tambah Sesi Kuliah</button>
        </div>
        <div class="table-responsive">
            <table id="datatable" class="table" style="width:100%">
                <thead>
                    <tr>
                        <th colspan="2">Sesi Mata Kuliah</th>
                    </tr>
                </thead>
                <tbody>
                    @if (isset($sesi_kuliahs['sesi_kuliah']))
                    @foreach ($sesi_kuliahs['sesi_kuliah'] as $sesi_kuliah)
                        <tr>
                            <td>
                                {{$sesi_kuliah['awal_waktu']}}
                            </td>
                            <td>
                                {{$sesi_kuliah['akhir_waktu']}}
                            </td>
                            <td>
                                <form action="{{ route('sesi-kuliah.generateqr') }}" method="post">
                                    @csrf
                                    <input type="hidden" name="kode_sesi" value="{{$sesi_kuliah['kode_sesi']}}">
                                    <button type="submit" class="btn btn-primary">Tampilkan QR</button>
                                </form>
                            </td>
                        </tr>
                    @endforeach
                    @else
                        <tr>
                            <td colspan="4">Tidak ada data</td>
                        </tr>
                    @endif
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="tambah_role" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <h1 class="modal-title fs-5" id="exampleModalLabel">Tambah Mata Kuliah</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <form action="{{ route('sesi-kuliah.create') }}" method="post">
            <div class="modal-body">
                @csrf
                <input type="hidden" name="code" value="{{$sesi_kuliahs['mata_kuliah_code']}}">
                <div class="mb-3">
                    <label class="form-label">Waktu Mulai</label>
                    <div class="d-flex">
                        <input id="waktumulai" class="form-control" placeholder="HH:MM" name="awal_waktu"/>
                        <button type="button" class="btn btn-primary" onclick="showpickers('waktumulai')"><i class="fa fa-clock-o"></i></button>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Waktu Berakhir</label>
                    <div class="d-flex">
                        <input id="waktuberakhir" class="form-control" placeholder="HH:MM" name="akhir_waktu"/>
                        <button type="button" class="btn btn-primary" onclick="showpickers('waktuberakhir')"><i class="fa fa-clock-o"></i></button>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tanggal Sesi</label>
                    <div class="input-group date" id="datepicker">
                        <input type="text" class="form-control" name="tanggal_sesi"/>
                        <span class="input-group-append"></span>
                    </div>              
                </div>
                <div class="timepicker"></div>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
        </div>
    </div>
</div>
@endsection

@push('script')
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.bootstrap4.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.10.0/js/bootstrap-datepicker.min.js"></script>
    <script src="{{ asset('/vendor/timepicker.js') }}"></script>
    <script>
        $(function(){
            $('#datepicker').datepicker({
                format: "dd/m/yyyy",
            });
        });
    </script>

    
@endpush
